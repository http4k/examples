package dev.langchain4j.rag.query

import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Objects

/**
 * Represents a query from the user intended for retrieving relevant [Content]s.
 * <br></br>
 * Currently, it is limited to text,
 * but future extensions may include support for other modalities (e.g., images, audio, video, etc.).
 * <br></br>
 * Includes [Metadata] that may be useful or necessary for retrieval or augmentation.
 */
class Query {
    private val text: String
    private val metadata: Metadata?

    constructor(text: String?) {
        this.text = ValidationUtils.ensureNotBlank(text, "text")
        this.metadata = null
    }

    constructor(text: String?, metadata: Metadata?) {
        this.text = ValidationUtils.ensureNotBlank(text, "text")
        this.metadata = ValidationUtils.ensureNotNull(metadata, "metadata")
    }

    fun text(): String {
        return text
    }

    fun metadata(): Metadata? {
        return metadata
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Query
        return this.text == that.text
                && this.metadata == that.metadata
    }

    override fun hashCode(): Int {
        return Objects.hash(text, metadata)
    }

    override fun toString(): String {
        return "Query {" +
                " text = " + Utils.quoted(text) +
                ", metadata = " + metadata +
                " }"
    }

    companion object {
        fun from(text: String?): Query {
            return Query(text)
        }

        fun from(text: String?, metadata: Metadata?): Query {
            return Query(text, metadata)
        }
    }
}
