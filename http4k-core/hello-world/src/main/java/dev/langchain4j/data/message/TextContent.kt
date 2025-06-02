package dev.langchain4j.data.message

import dev.langchain4j.internal.Utils
import java.util.Objects

/**
 * Represents a text content.
 */
class TextContent(text: String?) : Content {
    private val text: String = text!!

    /**
     * Returns the text.
     * @return the text.
     */
    fun text(): String {
        return text
    }

    override fun type(): ContentType {
        return ContentType.TEXT
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as TextContent
        return this.text == that.text
    }

    override fun hashCode(): Int {
        return Objects.hash(text)
    }

    override fun toString(): String {
        return "TextContent {" +
                " text = " + Utils.quoted(text) +
                " }"
    }

    companion object {
        /**
         * Creates a new text content.
         * @param text the text.
         * @return the text content.
         */
        fun from(text: String?): TextContent {
            return TextContent(text)
        }
    }
}
