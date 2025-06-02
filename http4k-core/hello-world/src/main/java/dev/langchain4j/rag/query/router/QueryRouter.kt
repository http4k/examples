package dev.langchain4j.rag.query.router

import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.query.Query

/**
 * Routes the given [Query] to one or multiple [ContentRetriever]s.
 * <br></br>
 * The goal is to ensure that [Content] is retrieved only from relevant data sources.
 * <br></br>
 * Some potential approaches include:
 * <pre>
 * - Using an LLM (see [LanguageModelQueryRouter])
 * - Using an [EmbeddingModel] (aka "semantic routing", see `EmbeddingModelTextClassifier` in the `langchain4j` module)
 * - Using keyword-based routing
 * - Route depending on the user (`query.metadata().chatMemoryId()`) and/or permissions
</pre> *
 *
 * @see DefaultQueryRouter
 *
 * @see LanguageModelQueryRouter
 */
interface QueryRouter {
    /**
     * Routes the given [Query] to one or multiple [ContentRetriever]s.
     *
     * @param query The [Query] to be routed.
     * @return A collection of one or more [ContentRetriever]s to which the [Query] should be routed.
     */
    fun route(query: Query): Collection<ContentRetriever?>
}
