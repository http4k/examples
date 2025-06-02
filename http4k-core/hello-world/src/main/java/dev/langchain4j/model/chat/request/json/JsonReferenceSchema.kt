package dev.langchain4j.model.chat.request.json

import dev.langchain4j.internal.Utils
import java.util.Objects

/**
 * Can reference [JsonObjectSchema] when recursion is required.
 * When used, the [JsonObjectSchema.definitions] of the root JSON schema element
 * should contain an entry with a key equal to the [.reference] of this [JsonReferenceSchema].
 */
class JsonReferenceSchema(builder: Builder) :
    JsonSchemaElement {
    private val reference: String?

    init {
        this.reference = builder.reference
    }

    fun reference(): String? {
        return reference
    }

    override fun description(): String? {
        return null
    }

    class Builder {
        var reference: String? = null

        fun reference(reference: String?): Builder {
            this.reference = reference
            return this
        }

        fun build(): JsonReferenceSchema {
            return JsonReferenceSchema(this)
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as JsonReferenceSchema
        return this.reference == that.reference
    }

    override fun hashCode(): Int {
        return Objects.hash(reference)
    }

    override fun toString(): String {
        return "JsonReferenceSchema {" +
                "reference = " + Utils.quoted(reference) +
                " }"
    }

    companion object {
        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }
    }
}
