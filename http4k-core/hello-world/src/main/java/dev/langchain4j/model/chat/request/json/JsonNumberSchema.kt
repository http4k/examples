package dev.langchain4j.model.chat.request.json

import dev.langchain4j.internal.Utils
import java.util.Objects

class JsonNumberSchema : JsonSchemaElement {
    private val description: String?

    constructor() {
        this.description = null
    }

    constructor(builder: Builder) {
        this.description = builder.description
    }

    override fun description(): String? {
        return description
    }

    class Builder {
        var description: String? = null

        fun description(description: String?): Builder {
            this.description = description
            return this
        }

        fun build(): JsonNumberSchema {
            return JsonNumberSchema(this)
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as JsonNumberSchema
        return this.description == that.description
    }

    override fun hashCode(): Int {
        return Objects.hash(description)
    }

    override fun toString(): String {
        return "JsonNumberSchema {" +
                "description = " + Utils.quoted(description) +
                " }"
    }

    companion object {
        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }
    }
}
