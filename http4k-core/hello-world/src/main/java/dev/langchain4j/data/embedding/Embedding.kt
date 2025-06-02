package dev.langchain4j.data.embedding

import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Represents a dense vector embedding of a text.
 * This class encapsulates a float array that captures the "meaning" or semantic information of the text.
 * Texts with similar meanings will have their vectors located close to each other in the embedding space.
 * The embeddings are typically created by embedding models.
 * @see dev.langchain4j.model.embedding.EmbeddingModel
 */
class Embedding(vector: FloatArray) {
    private val vector: FloatArray =
        vector!!

    /**
     * Returns the vector.
     * @return the vector.
     */
    fun vector(): FloatArray {
        return vector
    }

    /**
     * Returns a copy of the vector as a list.
     * @return the vector as a list.
     */
    fun vectorAsList(): List<Float> {
        val list: MutableList<Float> = ArrayList(vector.size)
        for (f in vector) {
            list.add(f)
        }
        return list
    }

    /**
     * Normalize vector
     */
    fun normalize() {
        var norm = 0.0
        for (f in vector) {
            norm += (f * f).toDouble()
        }
        norm = sqrt(norm)
        if (abs(norm) < 1e-10) {
            return
        }
        for (i in vector.indices) {
            vector[i] /= norm.toFloat()
        }
    }

    /**
     * Returns the dimension of the vector.
     * @return the dimension of the vector.
     */
    fun dimension(): Int {
        return vector.size
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Embedding
        return vector.contentEquals(that.vector)
    }

    override fun hashCode(): Int {
        return vector.contentHashCode()
    }

    override fun toString(): String {
        return "Embedding {" +
                " vector = " + vector.contentToString() +
                " }"
    }

    companion object {
        /**
         * Creates a new Embedding from the given vector.
         * @param vector the vector, takes ownership of the array.
         * @return the new Embedding.
         */
        fun from(vector: FloatArray): Embedding {
            return Embedding(vector)
        }

        /**
         * Creates a new Embedding from the given vector.
         * @param vector the vector.
         * @return the new Embedding.
         */
        fun from(vector: List<Float>): Embedding {
            val array = FloatArray(vector.size)
            for (i in vector.indices) {
                array[i] = vector[i]
            }
            return Embedding(array)
        }
    }
}
