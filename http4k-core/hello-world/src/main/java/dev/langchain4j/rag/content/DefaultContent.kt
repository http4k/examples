package dev.langchain4j.rag.content

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.data.segment.TextSegment.Companion.from
import dev.langchain4j.internal.Utils
import java.util.Objects

/**
 * A default implementation of a [Content].
 * <br></br>
 * The class includes optional metadata which can store additional information about the content.
 * This metadata is supplementary and is intentionally excluded from equality and hash calculations.
 * See [.equals] and [.hashCode] for details.
 */
class DefaultContent @JvmOverloads constructor(
    textSegment: TextSegment,
    metadata: Map<ContentMetadata?, Any>? = java.util.Map.of()
) :
    Content {
    private val textSegment: TextSegment =
        textSegment!!
    private val metadata: Map<ContentMetadata?, Any> =
        Utils.copy(metadata)

    constructor(text: String?) : this(from(text))

    override fun textSegment(): TextSegment {
        return textSegment
    }

    override fun metadata(): Map<ContentMetadata?, Any> {
        return metadata
    }

    /**
     * Compares this `Content` with another object for equality.
     * <br></br>
     * The `metadata` field is intentionally excluded from the equality check. Metadata is considered
     * supplementary information and does not contribute to the core identity of the `Content`.
     */
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Content
        return this.textSegment == that.textSegment()
    }

    /**
     * Computes the hash code for this `Content`.
     * <br></br>
     * The `metadata` field is excluded from the hash code calculation. This ensures that two logically identical
     * `Content` objects with differing metadata produce the same hash code, maintaining consistent behavior in
     * hash-based collections.
     */
    override fun hashCode(): Int {
        return Objects.hash(textSegment)
    }

    override fun toString(): String {
        return "DefaultContent {" +
                " textSegment = " + textSegment +
                ", metadata = " + metadata +
                " }"
    }
}
