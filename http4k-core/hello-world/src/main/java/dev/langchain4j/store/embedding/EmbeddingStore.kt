package dev.langchain4j.store.embedding

import dev.langchain4j.data.embedding.Embedding
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.exception.UnsupportedFeatureException
import dev.langchain4j.internal.Utils
import dev.langchain4j.store.embedding.filter.Filter

/**
 * Represents a store for embeddings, also known as a vector database.
 *
 * @param <Embedded> The class of the object that has been embedded. Typically, this is [dev.langchain4j.data.segment.TextSegment].
</Embedded> */
interface EmbeddingStore<Embedded> {
    /**
     * Adds a given embedding to the store.
     *
     * @param embedding The embedding to be added to the store.
     * @return The auto-generated ID associated with the added embedding.
     */
    fun add(embedding: Embedding?): String?

    /**
     * Adds a given embedding to the store.
     *
     * @param id        The unique identifier for the embedding to be added.
     * @param embedding The embedding to be added to the store.
     */
    fun add(id: String?, embedding: Embedding?)

    /**
     * Adds a given embedding and the corresponding content that has been embedded to the store.
     *
     * @param embedding The embedding to be added to the store.
     * @param embedded  Original content that was embedded.
     * @return The auto-generated ID associated with the added embedding.
     */
    fun add(embedding: Embedding?, embedded: Embedded): String?

    /**
     * Adds multiple embeddings to the store.
     *
     * @param embeddings A list of embeddings to be added to the store.
     * @return A list of auto-generated IDs associated with the added embeddings.
     */
    fun addAll(embeddings: List<Embedding?>?): List<String?>?

    /**
     * Adds multiple embeddings and their corresponding contents that have been embedded to the store.
     *
     * @param embeddings A list of embeddings to be added to the store.
     * @param embedded   A list of original contents that were embedded.
     * @return A list of auto-generated IDs associated with the added embeddings.
     */
    fun addAll(embeddings: List<Embedding?>, embedded: List<Embedded>?): List<String> {
        val ids = generateIds(embeddings.size)
        addAll(ids, embeddings, embedded)
        return ids
    }

    /**
     * Generates list of UUID strings
     *
     * @param n - dimension of list
     */
    fun generateIds(n: Int): List<String> {
        val ids: MutableList<String> = ArrayList()
        for (i in 0..<n) {
            ids.add(Utils.randomUUID())
        }
        return ids
    }

    /**
     * Adds multiple embeddings and their corresponding contents that have been embedded to the store.
     *
     * @param ids        A list of IDs associated with the added embeddings.
     * @param embeddings A list of embeddings to be added to the store.
     * @param embedded   A list of original contents that were embedded.
     */
    fun addAll(ids: List<String>?, embeddings: List<Embedding?>?, embedded: List<Embedded>?) {
        throw UnsupportedFeatureException("Not supported yet.")
    }

    /**
     * Removes a single embedding from the store by ID.
     *
     * @param id The unique ID of the embedding to be removed.
     */
    fun remove(id: String) {
        this.removeAll(listOf(id))
    }

    /**
     * Removes all embeddings that match the specified IDs from the store.
     *
     * @param ids A collection of unique IDs of the embeddings to be removed.
     */
    fun removeAll(ids: Collection<String?>?) {
        throw UnsupportedFeatureException("Not supported yet.")
    }

    /**
     * Removes all embeddings that match the specified [Filter] from the store.
     *
     * @param filter The filter to be applied to the [Metadata] of the [TextSegment] during removal.
     * Only embeddings whose `TextSegment`'s `Metadata`
     * match the `Filter` will be removed.
     */
    fun removeAll(filter: Filter?) {
        throw UnsupportedFeatureException("Not supported yet.")
    }

    /**
     * Removes all embeddings from the store.
     */
    fun removeAll() {
        throw UnsupportedFeatureException("Not supported yet.")
    }

    /**
     * Searches for the most similar (closest in the embedding space) [Embedding]s.
     * <br></br>
     * All search criteria are defined inside the [EmbeddingSearchRequest].
     * <br></br>
     * [EmbeddingSearchRequest.filter] can be used to filter by various metadata entries (e.g., user/memory ID).
     * Please note that not all [EmbeddingStore] implementations support [Filter]ing.
     *
     * @param request A request to search in an [EmbeddingStore]. Contains all search criteria.
     * @return An [EmbeddingSearchResult] containing all found [Embedding]s.
     */
    fun search(request: EmbeddingSearchRequest?): EmbeddingSearchResult<Embedded>?
}
