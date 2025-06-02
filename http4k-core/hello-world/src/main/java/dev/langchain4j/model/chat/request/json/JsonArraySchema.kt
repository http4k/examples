package dev.langchain4j.model.chat.request.json

import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Objects

class JsonArraySchema(builder: Builder) : JsonSchemaElement {
    private val description: String?
    private val items: JsonSchemaElement

    init {
        this.description = builder.description
        this.items = ValidationUtils.ensureNotNull(builder.items, "items")
    }

    override fun description(): String? {
        return description
    }

    fun items(): JsonSchemaElement {
        return items
    }

    class Builder {
        var description: String? = null
        var items: JsonSchemaElement? = null

        fun description(description: String?): Builder {
            this.description = description
            return this
        }

        fun items(items: JsonSchemaElement?): Builder {
            this.items = items
            return this
        }

        fun build(): JsonArraySchema {
            return JsonArraySchema(this)
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as JsonArraySchema
        return this.description == that.description
                && this.items == that.items
    }

    override fun hashCode(): Int {
        return Objects.hash(description, items)
    }

    override fun toString(): String {
        return "JsonArraySchema {" +
                "description = " + Utils.quoted(description) +
                ", items = " + items +
                " }"
    }

    companion object {
        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }
    }
}
