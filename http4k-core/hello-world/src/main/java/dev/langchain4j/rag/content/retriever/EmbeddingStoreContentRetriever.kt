package dev.langchain4j.rag.content.retriever

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.internal.Utils
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.rag.content.Content
import dev.langchain4j.rag.content.ContentMetadata
import dev.langchain4j.rag.query.Query
import dev.langchain4j.spi.ServiceHelper
import dev.langchain4j.spi.model.embedding.EmbeddingModelFactory
import dev.langchain4j.store.embedding.EmbeddingMatch
import dev.langchain4j.store.embedding.EmbeddingSearchRequest
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.filter.Filter
import java.util.Map
import java.util.function.Function
import java.util.stream.Collectors

/**
 * A [ContentRetriever] that retrieves from an [EmbeddingStore].
 * <br></br>
 * By default, it retrieves the 3 most similar [Content]s to the provided [Query],
 * without any [Filter]ing.
 * <br></br>
 * <br></br>
 * Configurable parameters (optional):
 * <br></br>
 * - `displayName`: Display name for logging purposes, e.g. when multiple instances are used.
 * <br></br>
 * - `maxResults`: The maximum number of [Content]s to retrieve.
 * <br></br>
 * - `dynamicMaxResults`: It is a [Function] that accepts a [Query] and returns a `maxResults` value.
 * It can be used to dynamically define `maxResults` value, depending on factors such as the query,
 * the user (using Metadata#chatMemoryId()} from [Query.metadata]), etc.
 * <br></br>
 * - `minScore`: The minimum relevance score for the returned [Content]s.
 * [Content]s scoring below `#minScore` are excluded from the results.
 * <br></br>
 * - `dynamicMinScore`: It is a [Function] that accepts a [Query] and returns a `minScore` value.
 * It can be used to dynamically define `minScore` value, depending on factors such as the query,
 * the user (using Metadata#chatMemoryId()} from [Query.metadata]), etc.
 * <br></br>
 * - `filter`: The [Filter] that will be applied to a [dev.langchain4j.data.document.Metadata] in the
 * [Content.textSegment].
 * <br></br>
 * - `dynamicFilter`: It is a [Function] that accepts a [Query] and returns a `filter` value.
 * It can be used to dynamically define `filter` value, depending on factors such as the query,
 * the user (using Metadata#chatMemoryId()} from [Query.metadata]), etc.
 */
class EmbeddingStoreContentRetriever private constructor(
    displayName: String?,
    embeddingStore: EmbeddingStore<TextSegment>?,
    embeddingModel: EmbeddingModel?,
    dynamicMaxResults: Function<Query, Int>?,
    dynamicMinScore: Function<Query, Double>?,
    dynamicFilter: Function<Query, Filter?>?
) :
    ContentRetriever {
    private val embeddingStore: EmbeddingStore<TextSegment>
    private val embeddingModel: EmbeddingModel

    private val maxResultsProvider: Function<Query, Int>?
    private val minScoreProvider: Function<Query, Double>?
    private val filterProvider: Function<Query, Filter?>?

    private val displayName: String

    constructor(
        embeddingStore: EmbeddingStore<TextSegment>?,
        embeddingModel: EmbeddingModel?
    ) : this(
        DEFAULT_DISPLAY_NAME,
        embeddingStore,
        embeddingModel,
        DEFAULT_MAX_RESULTS,
        DEFAULT_MIN_SCORE,
        DEFAULT_FILTER
    )

    constructor(
        embeddingStore: EmbeddingStore<TextSegment>?,
        embeddingModel: EmbeddingModel?,
        maxResults: Int
    ) : this(
        DEFAULT_DISPLAY_NAME,
        embeddingStore,
        embeddingModel,
        Function<Query, Int> { query: Query? -> maxResults },
        DEFAULT_MIN_SCORE,
        DEFAULT_FILTER
    )

    constructor(
        embeddingStore: EmbeddingStore<TextSegment>?,
        embeddingModel: EmbeddingModel?,
        maxResults: Int,
        minScore: Double
    ) : this(
        DEFAULT_DISPLAY_NAME,
        embeddingStore,
        embeddingModel,
        Function<Query, Int> { query: Query? -> maxResults },
        Function<Query, Double> { query: Query? -> minScore },
        DEFAULT_FILTER
    )

    init {
        this.displayName = Utils.getOrDefault(displayName, DEFAULT_DISPLAY_NAME)
        this.embeddingStore = embeddingStore!!
        this.embeddingModel = Utils.getOrDefault(
            embeddingModel
        ) { loadEmbeddingModel() }!!
        this.maxResultsProvider = Utils.getOrDefault(dynamicMaxResults, DEFAULT_MAX_RESULTS)
        this.minScoreProvider = Utils.getOrDefault(dynamicMinScore, DEFAULT_MIN_SCORE)
        this.filterProvider = Utils.getOrDefault(dynamicFilter, DEFAULT_FILTER)
    }

    class EmbeddingStoreContentRetrieverBuilder internal constructor() {
        private var displayName: String? = null
        private var embeddingStore: EmbeddingStore<TextSegment>? = null
        private var embeddingModel: EmbeddingModel? = null
        private var dynamicMaxResults: Function<Query, Int>? = null
        private var dynamicMinScore: Function<Query, Double>? = null
        private var dynamicFilter: Function<Query, Filter?>? = null

        fun maxResults(maxResults: Int?): EmbeddingStoreContentRetrieverBuilder {
            if (maxResults != null) {
                dynamicMaxResults =
                    Function { query: Query? -> maxResults }
            }
            return this
        }

        fun minScore(minScore: Double?): EmbeddingStoreContentRetrieverBuilder {
            if (minScore != null) {
                dynamicMinScore =
                    Function { query: Query? -> minScore }
            }
            return this
        }

        fun filter(filter: Filter?): EmbeddingStoreContentRetrieverBuilder {
            if (filter != null) {
                dynamicFilter =
                    Function { query: Query? -> filter }
            }
            return this
        }

        fun displayName(displayName: String?): EmbeddingStoreContentRetrieverBuilder {
            this.displayName = displayName
            return this
        }

        fun embeddingStore(embeddingStore: EmbeddingStore<TextSegment>?): EmbeddingStoreContentRetrieverBuilder {
            this.embeddingStore = embeddingStore
            return this
        }

        fun embeddingModel(embeddingModel: EmbeddingModel?): EmbeddingStoreContentRetrieverBuilder {
            this.embeddingModel = embeddingModel
            return this
        }

        fun dynamicMaxResults(dynamicMaxResults: Function<Query, Int>?): EmbeddingStoreContentRetrieverBuilder {
            this.dynamicMaxResults = dynamicMaxResults
            return this
        }

        fun dynamicMinScore(dynamicMinScore: Function<Query, Double>?): EmbeddingStoreContentRetrieverBuilder {
            this.dynamicMinScore = dynamicMinScore
            return this
        }

        fun dynamicFilter(dynamicFilter: Function<Query, Filter?>?): EmbeddingStoreContentRetrieverBuilder {
            this.dynamicFilter = dynamicFilter
            return this
        }

        fun build(): EmbeddingStoreContentRetriever {
            return EmbeddingStoreContentRetriever(
                this.displayName,
                this.embeddingStore,
                this.embeddingModel,
                this.dynamicMaxResults,
                this.dynamicMinScore,
                this.dynamicFilter
            )
        }
    }

    override fun retrieve(query: Query): List<Content> {
        val embeddedQuery = embeddingModel.embed(query.text()).content()

        val searchRequest = EmbeddingSearchRequest.builder()
            .queryEmbedding(embeddedQuery)
            .maxResults(maxResultsProvider!!.apply(query))
            .minScore(minScoreProvider!!.apply(query))
            .filter(filterProvider!!.apply(query))
            .build()

        val searchResult = embeddingStore.search(searchRequest)

        return searchResult!!.matches().stream()
            .map<Content> { embeddingMatch: EmbeddingMatch<TextSegment> ->
                Content.Companion.from(
                    embeddingMatch.embedded(),
                    Map.of<ContentMetadata?, Any>(
                        ContentMetadata.SCORE, embeddingMatch.score(),
                        ContentMetadata.EMBEDDING_ID, embeddingMatch.embeddingId()
                    )
                )
            }
            .collect(Collectors.toList<Content>())
    }

    override fun toString(): String {
        return "EmbeddingStoreContentRetriever{" +
                "displayName='" + displayName + '\'' +
                '}'
    }

    companion object {
        val DEFAULT_MAX_RESULTS: Function<Query, Int> =
            Function { query: Query? -> 3 }
        val DEFAULT_MIN_SCORE: Function<Query, Double> =
            Function { query: Query? -> 0.0 }
        val DEFAULT_FILTER: Function<Query, Filter?> =
            Function { query: Query? -> null }

        const val DEFAULT_DISPLAY_NAME: String = "Default"

        private fun loadEmbeddingModel(): EmbeddingModel? {
            val factories = ServiceHelper.loadFactories(
                EmbeddingModelFactory::class.java
            )
            if (factories.size > 1) {
                throw RuntimeException(
                    "Conflict: multiple embedding models have been found in the classpath. " +
                            "Please explicitly specify the one you wish to use."
                )
            }

            for (factory in factories) {
                return factory.create()
            }

            return null
        }

        fun builder(): EmbeddingStoreContentRetrieverBuilder {
            return EmbeddingStoreContentRetrieverBuilder()
        }

        /**
         * Creates an instance of an `EmbeddingStoreContentRetriever` from the specified [EmbeddingStore]
         * and [EmbeddingModel] found through SPI (see [EmbeddingModelFactory]).
         */
        fun from(embeddingStore: EmbeddingStore<TextSegment>?): EmbeddingStoreContentRetriever {
            return builder().embeddingStore(embeddingStore).build()
        }
    }
}
