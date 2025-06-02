package dev.langchain4j.model.chat.request.json

import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Objects

class JsonEnumSchema(builder: Builder) : JsonSchemaElement {
    private val description: String?
    private val enumValues: List<String>

    init {
        this.description = builder.description
        this.enumValues = Utils.copy(ValidationUtils.ensureNotEmpty(builder.enumValues, "enumValues"))
    }

    override fun description(): String? {
        return description
    }

    fun enumValues(): List<String> {
        return enumValues
    }

    class Builder {
        var description: String? = null
        var enumValues: List<String>? = null

        fun description(description: String?): Builder {
            this.description = description
            return this
        }

        fun enumValues(enumValues: List<String>?): Builder {
            this.enumValues = enumValues
            return this
        }

        fun enumValues(vararg enumValues: String?): Builder {
            return enumValues(java.util.List.of(*enumValues))
        }

        fun build(): JsonEnumSchema {
            return JsonEnumSchema(this)
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as JsonEnumSchema
        return this.description == that.description
                && this.enumValues == that.enumValues
    }

    override fun hashCode(): Int {
        return Objects.hash(description, enumValues)
    }

    override fun toString(): String {
        return "JsonEnumSchema {" +
                "description = " + Utils.quoted(description) +
                ", enumValues = " + enumValues +
                " }"
    }

    companion object {
        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }
    }
}
