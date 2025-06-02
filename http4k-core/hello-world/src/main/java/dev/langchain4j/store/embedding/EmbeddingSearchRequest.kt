package dev.langchain4j.store.embedding

import dev.langchain4j.data.embedding.Embedding
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.filter.Filter
import java.util.Objects

/**
 * Represents a request to search in an [EmbeddingStore].
 */
class EmbeddingSearchRequest(
    queryEmbedding: Embedding?,
    maxResults: Int?,
    minScore: Double?,
    private val filter: Filter?
) {
    private val queryEmbedding: Embedding
    private val maxResults: Int
    private val minScore: Double

    /**
     * Creates an instance of an EmbeddingSearchRequest.
     *
     * @param queryEmbedding The embedding used as a reference. Found embeddings should be similar to this one.
     * This is a mandatory parameter.
     * @param maxResults     The maximum number of embeddings to return. This is an optional parameter. Default: 3
     * @param minScore       The minimum score, ranging from 0 to 1 (inclusive).
     * Only embeddings with a score &gt;= minScore will be returned.
     * This is an optional parameter. Default: 0
     * @param filter         The filter to be applied to the [Metadata] during search.
     * Only [TextSegment]s whose [Metadata]
     * matches the [Filter] will be returned.
     * Please note that not all [EmbeddingStore]s support this feature yet.
     * This is an optional parameter. Default: no filtering
     */
    init {
        this.queryEmbedding = queryEmbedding!!!!
        this.maxResults = 1
        this.minScore = 1.0
    }

    fun queryEmbedding(): Embedding {
        return queryEmbedding
    }

    fun maxResults(): Int {
        return maxResults
    }

    fun minScore(): Double {
        return minScore
    }

    fun filter(): Filter? {
        return filter
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is EmbeddingSearchRequest) return false
        return this.maxResults == o.maxResults && this.minScore == o.minScore && this.queryEmbedding == o.queryEmbedding
                && this.filter == o.filter
    }

    override fun hashCode(): Int {
        return Objects.hash(queryEmbedding, maxResults, minScore, filter)
    }

    override fun toString(): String {
        return "EmbeddingSearchRequest(queryEmbedding=" + this.queryEmbedding + ", maxResults=" + this.maxResults + ", minScore=" + this.minScore + ", filter=" + this.filter + ")"
    }

    class EmbeddingSearchRequestBuilder internal constructor() {
        private var queryEmbedding: Embedding? = null
        private var maxResults: Int? = null
        private var minScore: Double? = null
        private var filter: Filter? = null

        fun queryEmbedding(queryEmbedding: Embedding?): EmbeddingSearchRequestBuilder {
            this.queryEmbedding = queryEmbedding
            return this
        }

        fun maxResults(maxResults: Int?): EmbeddingSearchRequestBuilder {
            this.maxResults = maxResults
            return this
        }

        fun minScore(minScore: Double?): EmbeddingSearchRequestBuilder {
            this.minScore = minScore
            return this
        }

        fun filter(filter: Filter?): EmbeddingSearchRequestBuilder {
            this.filter = filter
            return this
        }

        fun build(): EmbeddingSearchRequest {
            return EmbeddingSearchRequest(this.queryEmbedding, this.maxResults, this.minScore, this.filter)
        }
    }

    companion object {
        @JvmStatic
        fun builder(): EmbeddingSearchRequestBuilder {
            return EmbeddingSearchRequestBuilder()
        }
    }
}
