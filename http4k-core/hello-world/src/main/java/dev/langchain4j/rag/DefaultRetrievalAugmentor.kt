package dev.langchain4j.rag

import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.data.segment.TextSegment.Companion.from
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.rag.content.Content
import dev.langchain4j.rag.content.aggregator.ContentAggregator
import dev.langchain4j.rag.content.aggregator.DefaultContentAggregator
import dev.langchain4j.rag.content.injector.ContentInjector
import dev.langchain4j.rag.content.injector.DefaultContentInjector
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.query.Query
import dev.langchain4j.rag.query.router.DefaultQueryRouter
import dev.langchain4j.rag.query.router.QueryRouter
import dev.langchain4j.rag.query.transformer.DefaultQueryTransformer
import dev.langchain4j.rag.query.transformer.QueryTransformer
import java.util.Collections
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.Collectors

/**
 * The default implementation of [RetrievalAugmentor] intended to be suitable for the majority of use cases.
 * <br></br>
 * <br></br>
 * It's important to note that while efforts will be made to avoid breaking changes,
 * the default behavior of this class may be updated in the future if it's found
 * that the current behavior does not adequately serve the majority of use cases.
 * Such changes would be made to benefit both current and future users.
 * <br></br>
 * <br></br>
 * This implementation is inspired by [this article](https://blog.langchain.dev/deconstructing-rag)
 * and [this paper](https://arxiv.org/abs/2312.10997).
 * It is recommended to review these resources for a better understanding of the concept.
 * <br></br>
 * <br></br>
 * This implementation orchestrates the flow between the following base components:
 * <pre>
 * - [QueryTransformer]
 * - [QueryRouter]
 * - [ContentRetriever]
 * - [ContentAggregator]
 * - [ContentInjector]
</pre> *
 * Visual representation of this flow can be found
 * [here](https://docs.langchain4j.dev/img/advanced-rag.png).
 * <br></br>
 * For each base component listed above, we offer several ready-to-use implementations,
 * each based on a recognized approach.
 * We intend to introduce more such implementations over time and welcome your contributions.
 * <br></br>
 * <br></br>
 * The flow is as follows:
 * <br></br>
 * 1. A [Query] (derived from an original [UserMessage]) is transformed
 * using a [QueryTransformer] into one or multiple [Query]s.
 * <br></br>
 * 2. Each [Query] is routed to the appropriate [ContentRetriever] using a [QueryRouter].
 * Each [ContentRetriever] retrieves one or multiple [Content]s using a [Query].
 * <br></br>
 * 3. All [Content]s retrieved by all [ContentRetriever]s using all [Query]s are
 * aggregated (fused/re-ranked/filtered/etc.) into a final list of [Content]s using a [ContentAggregator].
 * <br></br>
 * 4. Lastly, a final list of [Content]s is injected into the original [UserMessage]
 * using a [ContentInjector].
 * <br></br>
 * <br></br>
 * By default, each base component (except for [ContentRetriever], which needs to be provided by you)
 * is initialized with a sensible default implementation:
 * <pre>
 * - [DefaultQueryTransformer]
 * - [DefaultQueryRouter]
 * - [DefaultContentAggregator]
 * - [DefaultContentInjector]
</pre> *
 * Nonetheless, you are encouraged to use one of the advanced ready-to-use implementations or create a custom one.
 * <br></br>
 * <br></br>
 * When there is only a single [Query] and a single [ContentRetriever],
 * query routing and content retrieval are performed in the same thread.
 * Otherwise, an [Executor] is used to parallelize the processing.
 * By default, a modified (keepAliveTime is 1 second instead of 60 seconds) [Executors.newCachedThreadPool]
 * is used, but you can provide a custom [Executor] instance.
 *
 * @see DefaultQueryTransformer
 *
 * @see DefaultQueryRouter
 *
 * @see DefaultContentAggregator
 *
 * @see DefaultContentInjector
 */
class DefaultRetrievalAugmentor(
    queryTransformer: QueryTransformer?,
    queryRouter: QueryRouter?,
    contentAggregator: ContentAggregator?,
    contentInjector: ContentInjector?,
    executor: Executor?
) : RetrievalAugmentor {
    private val queryTransformer: QueryTransformer?
    private val queryRouter: QueryRouter
    private val contentAggregator: ContentAggregator?
    private val contentInjector: ContentInjector?
    private val executor: Executor?

    init {
        this.queryTransformer = Utils.getOrDefault(
            queryTransformer
        ) { DefaultQueryTransformer() }
        this.queryRouter = ValidationUtils.ensureNotNull(queryRouter, "queryRouter")
        this.contentAggregator = Utils.getOrDefault(
            contentAggregator
        ) { DefaultContentAggregator() }
        this.contentInjector = Utils.getOrDefault(
            contentInjector
        ) { DefaultContentInjector() }
        this.executor = Utils.getOrDefault(
            executor
        ) { createDefaultExecutor() }
    }

    override fun augment(augmentationRequest: AugmentationRequest): AugmentationResult {
        val chatMessage = augmentationRequest.chatMessage()
        val queryText: String?
        if (chatMessage is UserMessage) {
            queryText = chatMessage.singleText()
        } else {
            throw IllegalArgumentException("Unsupported message type: " + chatMessage!!.type())
        }
        val originalQuery: Query = Query.Companion.from(queryText, augmentationRequest.metadata())

        val queries = queryTransformer!!.transform(originalQuery)

        val queryToContents = process(
            queries!!
        )

        val contents = contentAggregator!!.aggregate(queryToContents)

        val augmentedChatMessage = contentInjector!!.inject(contents!!, chatMessage)

        return AugmentationResult.Companion.builder()
            .chatMessage(augmentedChatMessage)
            .contents(contents)
            .build()
    }

    private fun process(queries: Collection<Query?>): Map<Query?, Collection<List<Content?>?>> {
        if (queries.size == 1) {
            val query = queries.iterator().next()!!
            val retrievers = queryRouter.route(query)
            if (retrievers!!.size == 1) {
                val contentRetriever = retrievers.iterator().next()
                val contents = contentRetriever!!.retrieve(query)
                return Collections.singletonMap<Query?, Collection<List<Content?>?>>(query, listOf(contents))
            } else if (retrievers.size > 1) {
                val contents = retrieveFromAll(
                    retrievers, query
                ).join()
                return Collections.singletonMap(query, contents)
            } else {
                return emptyMap()
            }
        } else if (queries.size > 1) {
            val queryToFutureContents: MutableMap<Query, CompletableFuture<Collection<List<Content?>?>>> =
                ConcurrentHashMap()
//            queries.forEach(Consumer { query: Query ->
//                val futureContents =
//                    CompletableFuture.supplyAsync({ queryRouter.route(query) }, executor)
//                        .thenCompose(
//                            Function<Collection<ContentRetriever?>?, CompletionStage<Collection<List<Content?>?>>> { retrievers: Collection<ContentRetriever?> ->
//                                retrieveFromAll(
//                                    retrievers,
//                                    query
//                                )
//                            })
//                queryToFutureContents[query] = futureContents
//            })
            return join(queryToFutureContents)
        } else {
            return emptyMap()
        }
    }

    private fun retrieveFromAll(
        retrievers: Collection<ContentRetriever?>,
        query: Query
    ): CompletableFuture<Collection<List<Content?>?>> {
        val futureContents = retrievers.stream()
            .map { retriever: ContentRetriever? ->
                CompletableFuture.supplyAsync(
                    { retriever!!.retrieve(query) }, executor
                )
            }
            .collect(Collectors.toList())

        return TODO()
//        return CompletableFuture.allOf(*futureContents.toTypedArray<CompletableFuture<*>>())
//            .thenApply(Function<Void, Collection<List<Content?>?>> { ignored: Void? ->
//                futureContents.stream()
//                    .map { obj: CompletableFuture<List<Content?>?> -> obj.join() }
//                    .collect(Collectors.toList())
//            })
    }

    class DefaultRetrievalAugmentorBuilder internal constructor() {
        private var queryTransformer: QueryTransformer? = null
        private var queryRouter: QueryRouter? = null
        private var contentAggregator: ContentAggregator? = null
        private var contentInjector: ContentInjector? = null
        private var executor: Executor? = null

        fun contentRetriever(contentRetriever: ContentRetriever): DefaultRetrievalAugmentorBuilder {
            this.queryRouter = DefaultQueryRouter(ValidationUtils.ensureNotNull(contentRetriever, "contentRetriever"))
            return this
        }

        fun queryTransformer(queryTransformer: QueryTransformer?): DefaultRetrievalAugmentorBuilder {
            this.queryTransformer = queryTransformer
            return this
        }

        fun queryRouter(queryRouter: QueryRouter?): DefaultRetrievalAugmentorBuilder {
            this.queryRouter = queryRouter
            return this
        }

        fun contentAggregator(contentAggregator: ContentAggregator?): DefaultRetrievalAugmentorBuilder {
            this.contentAggregator = contentAggregator
            return this
        }

        fun contentInjector(contentInjector: ContentInjector?): DefaultRetrievalAugmentorBuilder {
            this.contentInjector = contentInjector
            return this
        }

        fun executor(executor: Executor?): DefaultRetrievalAugmentorBuilder {
            this.executor = executor
            return this
        }

        fun build(): DefaultRetrievalAugmentor {
            return DefaultRetrievalAugmentor(
                this.queryTransformer,
                this.queryRouter,
                this.contentAggregator,
                this.contentInjector,
                this.executor
            )
        }
    }

    companion object {
        private fun createDefaultExecutor(): ExecutorService {
            return ThreadPoolExecutor(
                0, Int.MAX_VALUE,
                1, TimeUnit.SECONDS,
                SynchronousQueue()
            )
        }

        private fun join(
            queryToFutureContents: Map<Query, CompletableFuture<Collection<List<Content?>?>>>
        ): Map<Query?, Collection<List<Content?>?>> {
            return TODO()
//            return CompletableFuture.allOf(*queryToFutureContents.values.toTypedArray<CompletableFuture<*>>())
//                .thenApply<Map<Query?, Collection<List<Content?>?>>>(
//                    Function<Void, Map<Query?, Collection<List<Content?>?>>> { ignored: Void? ->
//                        queryToFutureContents.entries.stream()
//                            .collect(
//                                Collectors.toMap<Map.Entry<Query, CompletableFuture<Collection<List<Content?>?>>>, Query?, Collection<List<Content?>?>>(
//                                    Function<Map.Entry<Query, CompletableFuture<Collection<List<Content?>?>>>, Query?> { java.util.Map.Entry.key },
//                                    Function<Map.Entry<Query, CompletableFuture<Collection<List<Content?>?>>>, Collection<List<Content?>?>> { entry: Map.Entry<Query, CompletableFuture<Collection<List<Content?>?>>> -> entry.value.join() }
//                                ))
//                    }
//                ).join()
        }

        fun builder(): DefaultRetrievalAugmentorBuilder {
            return DefaultRetrievalAugmentorBuilder()
        }
    }
}
