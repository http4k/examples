package dev.langchain4j.data.segment

import dev.langchain4j.data.document.Metadata
import dev.langchain4j.internal.Utils
import java.util.Objects

/**
 * Represents a semantically meaningful segment (chunk/piece/fragment) of a larger entity such as a document or chat conversation.
 * This might be a sentence, a paragraph, or any other discrete unit of text that carries meaning.
 * This class encapsulates a piece of text and its associated metadata.
 */
class TextSegment(text: String?, metadata: Metadata) {
    private val text: String = text!!
    private val metadata: Metadata =
        metadata!!

    /**
     * Returns the text.
     *
     * @return the text.
     */
    fun text(): String {
        return text
    }

    /**
     * Returns the metadata.
     *
     * @return the metadata.
     */
    fun metadata(): Metadata {
        return metadata
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as TextSegment
        return this.text == that.text
                && this.metadata == that.metadata
    }

    override fun hashCode(): Int {
        return Objects.hash(text, metadata)
    }

    override fun toString(): String {
        return "TextSegment {" +
                " text = " + Utils.quoted(text) +
                " metadata = " + metadata.toMap() +
                " }"
    }

    companion object {
        /**
         * Creates a new text segment.
         *
         * @param text the text.
         * @return the text segment.
         */
        @JvmStatic
        fun from(text: String?): TextSegment {
            return TextSegment(text, Metadata())
        }

        /**
         * Creates a new text segment.
         *
         * @param text     the text.
         * @param metadata the metadata.
         * @return the text segment.
         */
        fun from(text: String?, metadata: Metadata): TextSegment {
            return TextSegment(text, metadata)
        }

        /**
         * Creates a new text segment.
         *
         * @param text the text.
         * @return the text segment.
         */
        fun textSegment(text: String?): TextSegment {
            return from(text)
        }

        /**
         * Creates a new text segment.
         *
         * @param text     the text.
         * @param metadata the metadata.
         * @return the text segment.
         */
        fun textSegment(text: String?, metadata: Metadata): TextSegment {
            return from(text, metadata)
        }
    }
}
