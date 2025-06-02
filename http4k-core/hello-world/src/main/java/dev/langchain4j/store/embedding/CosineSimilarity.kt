package dev.langchain4j.store.embedding

import dev.langchain4j.data.embedding.Embedding
import dev.langchain4j.internal.Exceptions
import dev.langchain4j.internal.ValidationUtils
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Utility class for calculating cosine similarity between two vectors.
 */
object CosineSimilarity {
    /**
     * A small value to avoid division by zero.
     */
    const val EPSILON: Float = 1e-8f

    /**
     * Calculates cosine similarity between two vectors.
     *
     *
     * Cosine similarity measures the cosine of the angle between two vectors, indicating their directional similarity.
     * It produces a value in the range:
     *
     *
     * -1 indicates vectors are diametrically opposed (opposite directions).
     *
     *
     * 0 indicates vectors are orthogonal (no directional similarity).
     *
     *
     * 1 indicates vectors are pointing in the same direction (but not necessarily of the same magnitude).
     *
     *
     * Not to be confused with cosine distance ([0..2]), which quantifies how different two vectors are.
     *
     *
     * Embeddings of all-zeros vectors are considered orthogonal to all other vectors;
     * including other all-zeros vectors.
     *
     * @param embeddingA first embedding vector
     * @param embeddingB second embedding vector
     * @return cosine similarity in the range [-1..1]
     */
    fun between(embeddingA: Embedding, embeddingB: Embedding): Double {
        ValidationUtils.ensureNotNull(embeddingA, "embeddingA")
        ValidationUtils.ensureNotNull(embeddingB, "embeddingB")

        val vectorA = embeddingA.vector()
        val vectorB = embeddingB.vector()

        if (vectorA.size != vectorB.size) {
            throw Exceptions.illegalArgument(
                "Length of vector a (%s) must be equal to the length of vector b (%s)",
                vectorA.size, vectorB.size
            )
        }

        var dotProduct = 0.0
        var normA = 0.0
        var normB = 0.0

        for (i in vectorA.indices) {
            dotProduct += (vectorA[i] * vectorB[i]).toDouble()
            normA += (vectorA[i] * vectorA[i]).toDouble()
            normB += (vectorB[i] * vectorB[i]).toDouble()
        }

        // Avoid division by zero.
        return dotProduct / max(sqrt(normA) * sqrt(normB), EPSILON.toDouble())
    }

    /**
     * Converts relevance score into cosine similarity.
     *
     * @param relevanceScore Relevance score in the range [0..1] where 0 is not relevant and 1 is relevant.
     * @return Cosine similarity in the range [-1..1] where -1 is not relevant and 1 is relevant.
     */
    fun fromRelevanceScore(relevanceScore: Double): Double {
        return relevanceScore * 2 - 1
    }
}
