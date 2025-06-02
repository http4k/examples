package dev.langchain4j.model.chat.request.json

import dev.langchain4j.internal.Utils
import java.util.Arrays
import java.util.Objects

class JsonAnyOfSchema(builder: Builder) : JsonSchemaElement {
    private val description: String?
    private val anyOf: List<JsonSchemaElement>

    init {
        this.description = builder.description
        this.anyOf = Utils.copy(builder.anyOf!!)
    }

    override fun description(): String? {
        return description
    }

    fun anyOf(): List<JsonSchemaElement> {
        return anyOf
    }

    class Builder {
        var description: String? = null
        var anyOf: List<JsonSchemaElement>? = null

        fun description(description: String?): Builder {
            this.description = description
            return this
        }

        fun anyOf(anyOf: List<JsonSchemaElement>?): Builder {
            this.anyOf = anyOf
            return this
        }

        fun anyOf(vararg anyOf: JsonSchemaElement?): Builder {
            return anyOf(Arrays.asList(*anyOf))
        }

        fun build(): JsonAnyOfSchema {
            return JsonAnyOfSchema(this)
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is JsonAnyOfSchema) return false
        return description == o.description
                && anyOf == o.anyOf
    }

    override fun hashCode(): Int {
        return Objects.hash(description, anyOf)
    }

    override fun toString(): String {
        return "JsonAnyOfSchema {" +
                "description = " + Utils.quoted(description) +
                ", anyOf = " + anyOf +
                " }"
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}
