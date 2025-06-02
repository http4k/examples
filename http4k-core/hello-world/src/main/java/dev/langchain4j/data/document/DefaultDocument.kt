package dev.langchain4j.data.document

import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Objects

/**
 * A default implementation of a [Document].
 */
class DefaultDocument @JvmOverloads constructor(text: String?, metadata: Metadata = Metadata()) :
    Document {
    private val text: String = ValidationUtils.ensureNotBlank(text, "text")
    private val metadata: Metadata =
        ValidationUtils.ensureNotNull(
            metadata,
            "metadata"
        )

    override fun text(): String? {
        return text
    }

    override fun metadata(): Metadata {
        return metadata
    }

    override fun equals(obj: Any?): Boolean {
        if (obj === this) return true
        if (obj == null || obj.javaClass != this.javaClass) return false
        val that = obj as DefaultDocument
        return this.text == that.text &&
                this.metadata == that.metadata
    }

    override fun hashCode(): Int {
        return Objects.hash(text, metadata)
    }

    override fun toString(): String {
        return "DefaultDocument {" +
                " text = " + Utils.quoted(text) +
                ", metadata = " + metadata +
                " }"
    }
}
