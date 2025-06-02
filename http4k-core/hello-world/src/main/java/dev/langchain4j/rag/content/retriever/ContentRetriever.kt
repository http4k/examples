package dev.langchain4j.rag.content.retriever

import dev.langchain4j.rag.content.Content
import dev.langchain4j.rag.query.Query

/**
 * Retrieves [Content]s from an underlying data source using a given [Query].
 * <br></br>
 * The goal is to retrieve only relevant [Content]s in relation to a given [Query].
 * <br></br>
 * The underlying data source can be virtually anything:
 * <pre>
 * - Embedding (vector) store (see [EmbeddingStoreContentRetriever])
 * - Full-text search engine (see `AzureAiSearchContentRetriever` in the `langchain4j-azure-ai-search` module)
 * - Hybrid of vector and full-text search (see `AzureAiSearchContentRetriever` in the `langchain4j-azure-ai-search` module)
 * - Web Search Engine (see [WebSearchContentRetriever])
 * - Knowledge graph (see `Neo4jContentRetriever` in the `langchain4j-community-neo4j-retriever` module)
 * - SQL database (see `SqlDatabaseContentRetriever` in the `langchain4j-experimental-sql` module)
 * - etc.
</pre> *
 *
 * @see EmbeddingStoreContentRetriever
 *
 * @see WebSearchContentRetriever
 */
interface ContentRetriever {
    /**
     * Retrieves relevant [Content]s using a given [Query].
     * The [Content]s are sorted by relevance, with the most relevant [Content]s appearing
     * at the beginning of the returned `List<Content>`.
     *
     * @param query The [Query] to use for retrieval.
     * @return A list of retrieved [Content]s.
     */
    fun retrieve(query: Query): List<Content>
}
