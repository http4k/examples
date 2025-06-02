package dev.langchain4j.rag.query.transformer

import dev.langchain4j.rag.query.Query

/**
 * Transforms the given [Query] into one or multiple [Query]s.
 * <br></br>
 * The goal is to enhance retrieval quality by modifying or expanding the original [Query].
 * <br></br>
 * Some known approaches to improve retrieval include:
 * <pre>
 * - Query compression (see [CompressingQueryTransformer])
 * - Query expansion (see [ExpandingQueryTransformer])
 * - Query re-writing
 * - Step-back prompting
 * - Hypothetical document embeddings (HyDE)
</pre> *
 * Additional details can be found [here](https://blog.langchain.dev/query-transformations/).
 *
 * @see DefaultQueryTransformer
 *
 * @see CompressingQueryTransformer
 *
 * @see ExpandingQueryTransformer
 */
interface QueryTransformer {
    /**
     * Transforms the given [Query] into one or multiple [Query]s.
     *
     * @param query The [Query] to be transformed.
     * @return A collection of one or more [Query]s derived from the original [Query].
     */
    fun transform(query: Query): Collection<Query>
}

