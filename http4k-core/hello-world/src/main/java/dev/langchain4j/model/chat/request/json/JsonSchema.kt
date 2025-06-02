package dev.langchain4j.model.chat.request.json

import dev.langchain4j.internal.Utils
import java.util.Objects

class JsonSchema private constructor(builder: Builder) {
    private val name: String?
    private val rootElement: JsonSchemaElement?

    init {
        this.name = builder.name
        this.rootElement = builder.rootElement
    }

    fun name(): String? {
        return name
    }

    fun rootElement(): JsonSchemaElement? {
        return rootElement
    }

    class Builder {
        var name: String? = null
        var rootElement: JsonSchemaElement? = null

        fun name(name: String?): Builder {
            this.name = name
            return this
        }

        fun rootElement(rootElement: JsonSchemaElement?): Builder {
            this.rootElement = rootElement
            return this
        }

        fun build(): JsonSchema {
            return JsonSchema(this)
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as JsonSchema
        return this.name == that.name
                && this.rootElement == that.rootElement
    }

    override fun hashCode(): Int {
        return Objects.hash(name, rootElement)
    }

    override fun toString(): String {
        return "JsonSchema {" +
                " name = " + Utils.quoted(name) +
                ", rootElement = " + rootElement +
                " }"
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}
