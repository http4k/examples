package dev.langchain4j.store.embedding

import dev.langchain4j.data.embedding.Embedding
import dev.langchain4j.internal.ValidationUtils
import java.util.Objects

/**
 * Represents a matched embedding along with its relevance score (derivative of cosine distance), ID, and original embedded content.
 *
 * @param <Embedded> The class of the object that has been embedded. Typically, it is [dev.langchain4j.data.segment.TextSegment].
</Embedded> */
class EmbeddingMatch<Embedded>(
    score: Double,
    embeddingId: String?,
    private val embedding: Embedding,
    private val embedded: Embedded
) {
    private val score: Double = ValidationUtils.ensureNotNull(score, "score")
    private val embeddingId: String = ValidationUtils.ensureNotBlank(embeddingId, "embeddingId")

    /**
     * Returns the relevance score (derivative of cosine distance) of this embedding compared to
     * a reference embedding during a search.
     * The current implementation assumes that the embedding store uses cosine distance when comparing embeddings.
     *
     * @return Relevance score, ranging from 0 (not relevant) to 1 (highly relevant).
     */
    fun score(): Double {
        return score
    }

    /**
     * The ID of the embedding assigned when adding this embedding to the store.
     * @return The ID of the embedding assigned when adding this embedding to the store.
     */
    fun embeddingId(): String {
        return embeddingId
    }

    /**
     * Returns the embedding that has been matched.
     * @return The embedding that has been matched.
     */
    fun embedding(): Embedding {
        return embedding
    }

    /**
     * Returns the original content that was embedded.
     * @return The original content that was embedded. Typically, this is a [dev.langchain4j.data.segment.TextSegment].
     */
    fun embedded(): Embedded {
        return embedded
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as EmbeddingMatch<*>
        return Objects.equals(this.score, that.score)
                && this.embeddingId == that.embeddingId
                && this.embedding == that.embedding
                && this.embedded == that.embedded
    }

    override fun hashCode(): Int {
        return Objects.hash(score, embeddingId, embedding, embedded)
    }

    override fun toString(): String {
        return "EmbeddingMatch {" +
                " score = " + score +
                ", embedded = " + embedded +
                ", embeddingId = " + embeddingId +
                ", embedding = " + embedding +
                " }"
    }
}
