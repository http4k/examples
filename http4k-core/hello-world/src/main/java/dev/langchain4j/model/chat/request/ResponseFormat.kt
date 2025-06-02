package dev.langchain4j.model.chat.request

import dev.langchain4j.model.chat.request.json.JsonSchema
import java.util.Objects

class ResponseFormat private constructor(builder: Builder) {
    private val type: ResponseFormatType
    private val jsonSchema: JsonSchema?

    init {
        this.type = builder.type!!
        this.jsonSchema = builder.jsonSchema
        check(!(jsonSchema != null && type != ResponseFormatType.JSON)) { "JsonSchema can be specified only when type=JSON" }
    }

    fun type(): ResponseFormatType {
        return type
    }

    fun jsonSchema(): JsonSchema? {
        return jsonSchema
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ResponseFormat
        return this.type == that.type
                && this.jsonSchema == that.jsonSchema
    }

    override fun hashCode(): Int {
        return Objects.hash(type, jsonSchema)
    }

    override fun toString(): String {
        return "ResponseFormat {" +
                " type = " + type +
                ", jsonSchema = " + jsonSchema +
                " }"
    }

    class Builder {
        var type: ResponseFormatType? = null
        var jsonSchema: JsonSchema? = null

        fun type(type: ResponseFormatType?): Builder {
            this.type = type
            return this
        }

        fun jsonSchema(jsonSchema: JsonSchema?): Builder {
            this.jsonSchema = jsonSchema
            return this
        }

        fun build(): ResponseFormat {
            return ResponseFormat(this)
        }
    }

    companion object {
        val TEXT: ResponseFormat = builder().type(ResponseFormatType.TEXT).build()
        val JSON: ResponseFormat = builder().type(ResponseFormatType.JSON).build()

        fun builder(): Builder {
            return Builder()
        }
    }
}
