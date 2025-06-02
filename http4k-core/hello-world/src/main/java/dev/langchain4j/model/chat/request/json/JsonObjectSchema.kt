package dev.langchain4j.model.chat.request.json

import dev.langchain4j.internal.Utils
import java.util.Arrays
import java.util.Objects

class JsonObjectSchema(builder: Builder) : JsonSchemaElement {
    private val description: String?
    private val properties: Map<String, JsonSchemaElement>
    private val required: List<String>
    private val additionalProperties: Boolean?
    private val definitions: Map<String, JsonSchemaElement>

    init {
        this.description = builder.description
        this.properties = Utils.copy(builder.properties)
        this.required = Utils.copy(builder.required)
        this.additionalProperties = builder.additionalProperties
        this.definitions = Utils.copy(builder.definitions)
    }

    override fun description(): String? {
        return description
    }

    fun properties(): Map<String, JsonSchemaElement> {
        return properties
    }

    fun required(): List<String> {
        return required
    }

    fun additionalProperties(): Boolean? {
        return additionalProperties
    }

    /**
     * Used together with [JsonReferenceSchema] when recursion is required.
     */
    fun definitions(): Map<String, JsonSchemaElement> {
        return definitions
    }

    class Builder {
        var description: String? = null
        val properties: MutableMap<String, JsonSchemaElement> = LinkedHashMap()
        var required: List<String>? = null
        var additionalProperties: Boolean? = null
        var definitions: Map<String, JsonSchemaElement>? = null

        fun description(description: String?): Builder {
            this.description = description
            return this
        }

        /**
         * Adds all properties in the parameter Map to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addProperty
         * @see .addStringProperty
         * @see .addStringProperty
         * @see .addIntegerProperty
         * @see .addIntegerProperty
         * @see .addNumberProperty
         * @see .addNumberProperty
         * @see .addBooleanProperty
         * @see .addBooleanProperty
         * @see .addEnumProperty
         * @see .addEnumProperty
         */
        fun addProperties(properties: Map<String, JsonSchemaElement>): Builder {
            this.properties.putAll(properties)
            return this
        }

        /**
         * Adds a single property to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addProperties
         * @see .addStringProperty
         * @see .addStringProperty
         * @see .addIntegerProperty
         * @see .addIntegerProperty
         * @see .addNumberProperty
         * @see .addNumberProperty
         * @see .addBooleanProperty
         * @see .addBooleanProperty
         * @see .addEnumProperty
         * @see .addEnumProperty
         */
        fun addProperty(name: String, jsonSchemaElement: JsonSchemaElement): Builder {
            properties[name] = jsonSchemaElement
            return this
        }

        /**
         * Adds a single string property to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addStringProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addStringProperty(name: String): Builder {
            properties[name] = JsonStringSchema()
            return this
        }

        /**
         * Adds a single string property with a description to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addStringProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addStringProperty(name: String, description: String?): Builder {
            properties[name] = JsonStringSchema.Companion.builder().description(description).build()
            return this
        }

        /**
         * Adds a single integer property to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addIntegerProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addIntegerProperty(name: String): Builder {
            properties[name] = JsonIntegerSchema()
            return this
        }

        /**
         * Adds a single integer property with a description to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addIntegerProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addIntegerProperty(name: String, description: String?): Builder {
            properties[name] = JsonIntegerSchema.Companion.builder().description(description).build()
            return this
        }

        /**
         * Adds a single number property to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addNumberProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addNumberProperty(name: String): Builder {
            properties[name] = JsonNumberSchema()
            return this
        }

        /**
         * Adds a single number property with a description to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addNumberProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addNumberProperty(name: String, description: String?): Builder {
            properties[name] = JsonNumberSchema.Companion.builder().description(description).build()
            return this
        }

        /**
         * Adds a single boolean property to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addBooleanProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addBooleanProperty(name: String): Builder {
            properties[name] = JsonBooleanSchema()
            return this
        }

        /**
         * Adds a single boolean property with a description to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addBooleanProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addBooleanProperty(name: String, description: String?): Builder {
            properties[name] = JsonBooleanSchema.Companion.builder().description(description).build()
            return this
        }

        /**
         * Adds a single enum property to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addEnumProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addEnumProperty(name: String, enumValues: List<String>?): Builder {
            properties[name] = JsonEnumSchema.Companion.builder().enumValues(enumValues).build()
            return this
        }

        /**
         * Adds a single enum property with a description to the properties of this JSON object.
         * Please note that [.required] should be set explicitly if you want the properties to be mandatory.
         *
         * @see .addEnumProperty
         * @see .addProperty
         * @see .addProperties
         */
        fun addEnumProperty(name: String, enumValues: List<String>?, description: String?): Builder {
            properties[name] =
                JsonEnumSchema.Companion.builder().enumValues(enumValues).description(description).build()
            return this
        }

        fun required(required: List<String>?): Builder {
            this.required = required
            return this
        }

        fun required(vararg required: String?): Builder {
            return required(Arrays.asList(*required))
        }

        fun additionalProperties(additionalProperties: Boolean?): Builder {
            this.additionalProperties = additionalProperties
            return this
        }

        /**
         * Used together with [JsonReferenceSchema] when recursion is required.
         */
        fun definitions(definitions: Map<String, JsonSchemaElement>?): Builder {
            this.definitions = definitions
            return this
        }

        fun build(): JsonObjectSchema {
            return JsonObjectSchema(this)
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as JsonObjectSchema
        return this.description == that.description
                && this.properties == that.properties
                && this.required == that.required
                && this.additionalProperties == that.additionalProperties
                && this.definitions == that.definitions
    }

    override fun hashCode(): Int {
        return Objects.hash(description, properties, required, additionalProperties, definitions)
    }

    override fun toString(): String {
        return "JsonObjectSchema {" +
                "description = " + Utils.quoted(description) +
                ", properties = " + properties +
                ", required = " + required +
                ", additionalProperties = " + additionalProperties +
                ", definitions = " + definitions +
                " }"
    }

    companion object {
        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }
    }
}
