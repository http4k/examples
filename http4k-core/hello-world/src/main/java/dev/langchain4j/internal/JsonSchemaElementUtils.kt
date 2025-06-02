package dev.langchain4j.internal

import dev.langchain4j.internal.Utils.generateUUIDFrom
import dev.langchain4j.model.chat.request.json.JsonAnyOfSchema
import dev.langchain4j.model.chat.request.json.JsonArraySchema
import dev.langchain4j.model.chat.request.json.JsonBooleanSchema
import dev.langchain4j.model.chat.request.json.JsonEnumSchema
import dev.langchain4j.model.chat.request.json.JsonIntegerSchema
import dev.langchain4j.model.chat.request.json.JsonNullSchema
import dev.langchain4j.model.chat.request.json.JsonNumberSchema
import dev.langchain4j.model.chat.request.json.JsonObjectSchema
import dev.langchain4j.model.chat.request.json.JsonReferenceSchema
import dev.langchain4j.model.chat.request.json.JsonSchemaElement
import dev.langchain4j.model.chat.request.json.JsonStringSchema
import dev.langchain4j.model.output.structured.Description
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Arrays
import java.util.Optional
import java.util.UUID
import java.util.stream.Collectors

object JsonSchemaElementUtils {
    private const val DEFAULT_UUID_DESCRIPTION = "String in a UUID format"

    fun jsonSchemaElementFrom(
        clazz: Class<*>,
        type: Type?,
        fieldDescription: String?,
        areSubFieldsRequiredByDefault: Boolean,
        visited: MutableMap<Class<*>?, VisitedClassMetadata>
    ): JsonSchemaElement {
        if (isJsonString(clazz)) {
            return JsonStringSchema.builder()
                .description(Optional.ofNullable(fieldDescription).orElse(descriptionFrom(clazz)))
                .build()
        }

        if (isJsonInteger(clazz)) {
            return JsonIntegerSchema.builder().description(fieldDescription).build()
        }

        if (isJsonNumber(clazz)) {
            return JsonNumberSchema.builder().description(fieldDescription).build()
        }

        if (isJsonBoolean(clazz)) {
            return JsonBooleanSchema.builder().description(fieldDescription).build()
        }

        if (clazz.isEnum) {
            return JsonEnumSchema.builder()
                .enumValues(
                    Arrays.stream(clazz.enumConstants)
                        .map { obj: Any -> obj.toString() }
                        .collect(Collectors.toList()))
                .description(Optional.ofNullable(fieldDescription).orElse(descriptionFrom(clazz)))
                .build()
        }

        if (clazz.isArray) {
            return JsonArraySchema.builder()
                .items(jsonSchemaElementFrom(clazz.componentType, null, null, areSubFieldsRequiredByDefault, visited))
                .description(fieldDescription)
                .build()
        }

        if (MutableCollection::class.java.isAssignableFrom(clazz)) {
            return JsonArraySchema.builder()
                .items(jsonSchemaElementFrom(getActualType(type)!!, null, null, areSubFieldsRequiredByDefault, visited))
                .description(fieldDescription)
                .build()
        }

        return jsonObjectOrReferenceSchemaFrom(clazz, fieldDescription, areSubFieldsRequiredByDefault, visited, false)
    }

    fun jsonObjectOrReferenceSchemaFrom(
        type: Class<*>,
        description: String?,
        areSubFieldsRequiredByDefault: Boolean,
        visited: MutableMap<Class<*>?, VisitedClassMetadata>,
        setDefinitions: Boolean
    ): JsonSchemaElement {
        if (visited.containsKey(type) && isCustomClass(type)) {
            val visitedClassMetadata = visited[type]
            val jsonSchemaElement = visitedClassMetadata!!.jsonSchemaElement
            if (jsonSchemaElement is JsonReferenceSchema) {
                visitedClassMetadata.recursionDetected = true
            }
            return jsonSchemaElement
        }

        val reference = generateUUIDFrom(type.name)
        val jsonReferenceSchema =
            JsonReferenceSchema.builder().reference(reference).build()
        visited[type] = VisitedClassMetadata(jsonReferenceSchema, reference, false)

        val properties: MutableMap<String, JsonSchemaElement> = LinkedHashMap()
        val required: MutableList<String> = ArrayList()
        for (field in type.declaredFields) {
            val fieldName = field.name
            if (Modifier.isStatic(field.modifiers) || fieldName == "__\$hits$" || fieldName.startsWith("this$")) {
                continue
            }
            if (isRequired(field, areSubFieldsRequiredByDefault)) {
                required.add(fieldName)
            }
            val fieldDescription = descriptionFrom(field)
            val jsonSchemaElement = jsonSchemaElementFrom(
                field.type, field.genericType,
                fieldDescription, areSubFieldsRequiredByDefault, visited
            )
            properties[fieldName] = jsonSchemaElement
        }

        val builder = JsonObjectSchema.builder()
            .description(Optional.ofNullable(description).orElse(descriptionFrom(type)))
            .addProperties(properties)
            .required(required)

        visited[type]!!.jsonSchemaElement = builder.build()

        if (setDefinitions) {
            val definitions: MutableMap<String, JsonSchemaElement> = LinkedHashMap()
            if (!definitions.isEmpty()) {
                builder.definitions(definitions)
            }
        }

        return builder.build()
    }

    private fun isRequired(field: Field, defaultValue: Boolean): Boolean {
        return defaultValue
    }

    private fun descriptionFrom(field: Field): String? {
        return descriptionFrom(
            field.getAnnotation(
                Description::class.java
            )
        )
    }

    private fun descriptionFrom(type: Class<*>): String? {
        if (type == UUID::class.java) {
            return DEFAULT_UUID_DESCRIPTION
        }
        return descriptionFrom(
            type.getAnnotation(
                Description::class.java
            )
        )
    }

    private fun descriptionFrom(description: Description?): String? {
        if (description == null) {
            return null
        }
        return java.lang.String.join(" ", *description.value)
    }

    private fun getActualType(type: Type?): Class<*>? {
        if (type is ParameterizedType) {
            val actualTypeArguments = type.actualTypeArguments
            if (actualTypeArguments.size == 1) {
                return actualTypeArguments[0] as Class<*>
            }
        }
        return null
    }

    fun isCustomClass(clazz: Class<*>): Boolean {
        if (clazz.getPackage() != null) {
            val packageName = clazz.getPackage().name
            return !packageName.startsWith("java.") && !packageName.startsWith("javax.") && !packageName.startsWith("jdk.") && !packageName.startsWith(
                "sun."
            ) && !packageName.startsWith("com.sun.")
        }

        return true
    }

    @JvmOverloads
    fun toMap(properties: Map<String?, JsonSchemaElement?>, strict: Boolean = false): Map<String, Map<String, Any?>> {
        val map: MutableMap<String, Map<String, Any?>> = LinkedHashMap()
        properties.forEach { (property: String?, value: JsonSchemaElement?) ->
            map[property!!] = toMap(value!!, strict)
        }
        return map
    }

    @JvmOverloads
    fun toMap(
        jsonSchemaElement: JsonSchemaElement,
        strict: Boolean = false,
        required: Boolean = true
    ): Map<String, Any?> {
        if (jsonSchemaElement is JsonObjectSchema) {
            val map: MutableMap<String, Any?> = LinkedHashMap()
            map["type"] = type("object", strict, required)

            if (jsonSchemaElement.description() != null) {
                map["description"] = jsonSchemaElement.description()
            }

            val properties: MutableMap<String, Map<String, Any?>> = LinkedHashMap()
            map["properties"] = properties

            if (strict) {
                // When using Structured Outputs with strict=true, all fields must be required.
                // See https://platform.openai.com/docs/guides/structured-outputs/supported-schemas?api-mode=chat#all-fields-must-be-required
                map["required"] = jsonSchemaElement.properties().keys.stream().toList()
            } else {
                if (jsonSchemaElement.required() != null) {
                    map["required"] = jsonSchemaElement.required()
                }
            }

            if (strict) {
                map["additionalProperties"] = false
            }

            return map
        } else if (jsonSchemaElement is JsonArraySchema) {
            val map: MutableMap<String, Any?> = LinkedHashMap()
            map["type"] = type("array", strict, required)
            if (jsonSchemaElement.description() != null) {
                map["description"] = jsonSchemaElement.description()
            }
            map["items"] = toMap(jsonSchemaElement.items(), strict)
            return map
        } else if (jsonSchemaElement is JsonEnumSchema) {
            val map: MutableMap<String, Any?> = LinkedHashMap()
            map["type"] = type("string", strict, required)
            if (jsonSchemaElement.description() != null) {
                map["description"] = jsonSchemaElement.description()
            }
            map["enum"] = jsonSchemaElement.enumValues()
            return map
        } else if (jsonSchemaElement is JsonStringSchema) {
            val map: MutableMap<String, Any?> = LinkedHashMap()
            map["type"] = type("string", strict, required)
            if (jsonSchemaElement.description() != null) {
                map["description"] = jsonSchemaElement.description()
            }
            return map
        } else if (jsonSchemaElement is JsonIntegerSchema) {
            val map: MutableMap<String, Any?> = LinkedHashMap()
            map["type"] = type("integer", strict, required)
            if (jsonSchemaElement.description() != null) {
                map["description"] = jsonSchemaElement.description()
            }
            return map
        } else if (jsonSchemaElement is JsonNumberSchema) {
            val map: MutableMap<String, Any?> = LinkedHashMap()
            map["type"] = type("number", strict, required)
            if (jsonSchemaElement.description() != null) {
                map["description"] = jsonSchemaElement.description()
            }
            return map
        } else if (jsonSchemaElement is JsonBooleanSchema) {
            val map: MutableMap<String, Any?> = LinkedHashMap()
            map["type"] = type("boolean", strict, required)
            if (jsonSchemaElement.description() != null) {
                map["description"] = jsonSchemaElement.description()
            }
            return map
        } else if (jsonSchemaElement is JsonReferenceSchema) {
            val map: MutableMap<String, Any?> = LinkedHashMap()
            val reference = jsonSchemaElement.reference()
            if (reference != null) {
                map["\$ref"] = "#/\$defs/$reference"
            }
            return map
        } else if (jsonSchemaElement is JsonAnyOfSchema) {
            val map: MutableMap<String, Any?> = LinkedHashMap()
            if (jsonSchemaElement.description() != null) {
                map["description"] = jsonSchemaElement.description()
            }
            val anyOf = jsonSchemaElement.anyOf().stream()
                .map { element: JsonSchemaElement -> toMap(element, strict) }
                .collect(Collectors.toList())
            map["anyOf"] = anyOf
            return map
        } else if (jsonSchemaElement is JsonNullSchema) {
            return java.util.Map.of<String, Any?>("type", "null")
        } else {
            throw IllegalArgumentException("Unknown type: " + jsonSchemaElement.javaClass)
        }
    }

    private fun type(type: String, strict: Boolean, required: Boolean): Any {
        return if (strict && !required) {
            // Emulating an optional parameter by using a union type with null.
            // See https://platform.openai.com/docs/guides/structured-outputs/supported-schemas?api-mode=chat#all-fields-must-be-required
            arrayOf(type, "null")
        } else {
            type
        }
    }

    fun isJsonInteger(type: Class<*>): Boolean {
        return type == Byte::class.javaPrimitiveType || type == Byte::class.java || type == Short::class.javaPrimitiveType || type == Short::class.java || type == Int::class.javaPrimitiveType || type == Int::class.java || type == Long::class.javaPrimitiveType || type == Long::class.java || type == BigInteger::class.java
    }

    fun isJsonNumber(type: Class<*>): Boolean {
        return type == Float::class.javaPrimitiveType || type == Float::class.java || type == Double::class.javaPrimitiveType || type == Double::class.java || type == BigDecimal::class.java
    }

    fun isJsonBoolean(type: Class<*>): Boolean {
        return type == Boolean::class.javaPrimitiveType || type == Boolean::class.java
    }

    fun isJsonString(type: Class<*>): Boolean {
        return type == String::class.java || type == Char::class.javaPrimitiveType || type == Char::class.java || CharSequence::class.java.isAssignableFrom(
            type
        )
                || type == UUID::class.java
    }

    fun isJsonArray(type: Class<*>): Boolean {
        return type.isArray || Iterable::class.java.isAssignableFrom(type)
    }

    class VisitedClassMetadata(
        var jsonSchemaElement: JsonSchemaElement,
        var reference: String,
        var recursionDetected: Boolean
    )
}
