package dev.langchain4j.agent.tool

import dev.langchain4j.internal.JsonSchemaElementUtils
import dev.langchain4j.internal.JsonSchemaElementUtils.VisitedClassMetadata
import dev.langchain4j.internal.Utils
import dev.langchain4j.model.chat.request.json.JsonObjectSchema
import dev.langchain4j.model.chat.request.json.JsonSchemaElement
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.util.Arrays
import java.util.Optional
import java.util.function.Function
import java.util.stream.Collectors

/**
 * Utility methods for [ToolSpecification]s.
 */
object ToolSpecifications {
    /**
     * Returns [ToolSpecification]s for all methods annotated with @[Tool] within the specified class.
     *
     * @param classWithTools the class.
     * @return the [ToolSpecification]s.
     */
    fun toolSpecificationsFrom(classWithTools: Class<*>): List<ToolSpecification> {
        val toolSpecifications = Arrays.stream(classWithTools.declaredMethods)
            .filter { method: Method -> method.isAnnotationPresent(Tool::class.java) }
            .map { obj: Method? -> toolSpecificationFrom(obj!!) }
            .collect(Collectors.toList())
        validateSpecifications(toolSpecifications)
        return toolSpecifications
    }

    /**
     * Returns [ToolSpecification]s for all methods annotated with @[Tool]
     * within the class of the specified object.
     *
     * @param objectWithTools the object.
     * @return the [ToolSpecification]s.
     */
    fun toolSpecificationsFrom(objectWithTools: Any): List<ToolSpecification> {
        return toolSpecificationsFrom(objectWithTools.javaClass)
    }

    /**
     * Validates all the [ToolSpecification]s. The validation checks for duplicate method names.
     * Throws [IllegalArgumentException] if validation fails
     *
     * @param toolSpecifications list of ToolSpecification to be validated.
     */
    @Throws(IllegalArgumentException::class)
    fun validateSpecifications(toolSpecifications: List<ToolSpecification>) {
        // Checks for duplicates methods

        val names: MutableSet<String?> = HashSet()
        for (toolSpecification in toolSpecifications) {
            require(names.add(toolSpecification.name())) {
                String.format(
                    "Tool names must be unique. The tool '%s' appears several times",
                    toolSpecification.name()
                )
            }
        }
    }

    /**
     * Returns the [ToolSpecification] for the given method annotated with @[Tool].
     *
     * @param method the method.
     * @return the [ToolSpecification].
     */
    fun toolSpecificationFrom(method: Method): ToolSpecification {
        val annotation = method.getAnnotation(Tool::class.java)

        val name = if (Utils.isNullOrBlank(annotation!!.name)) method.name else annotation.name

        var description = java.lang.String.join("\n", *annotation.value)
        if (description.isEmpty()) {
            description = null
        }

        val parameters = parametersFrom(method.parameters)

        return ToolSpecification.Companion.builder()
            .name(name)
            .description(description)
            .parameters(parameters)
            .build()
    }

    private fun parametersFrom(parameters: Array<Parameter>): JsonObjectSchema? {
        val properties: MutableMap<String, JsonSchemaElement> = LinkedHashMap()
        val required: MutableList<String> = ArrayList()

        val visited: Map<Class<*>, VisitedClassMetadata> = LinkedHashMap()

        for (parameter in parameters) {
            if (parameter.isAnnotationPresent(ToolMemoryId::class.java)) {
                continue
            }

            val isRequired = Optional.ofNullable(
                parameter.getAnnotation(
                    P::class.java
                )
            )
                .map(Function { obj: P -> obj.required })
                .orElse(true)

            properties[parameter.name] = jsonSchemaElementFrom(parameter, visited)
            if (isRequired) {
                required.add(parameter.name)
            }
        }

        val definitions: MutableMap<String, JsonSchemaElement> = LinkedHashMap()
//        visited.forEach { (clazz: Class<*>?, visitedClassMetadata: VisitedClassMetadata?) ->
//            if (visitedClassMetadata.recursionDetected) {
//                definitions[visitedClassMetadata.reference] = visitedClassMetadata.jsonSchemaElement
//            }
//        }

        if (properties.isEmpty()) {
            return null
        }

        return JsonObjectSchema.builder()
            .addProperties(properties)
            .required(required)
            .definitions(if (definitions.isEmpty()) null else definitions)
            .build()
    }

    private fun jsonSchemaElementFrom(
        parameter: Parameter,
        visited: Map<Class<*>, VisitedClassMetadata>
    ): JsonSchemaElement {
        val annotation = parameter.getAnnotation(P::class.java)
        val description = annotation?.value
        return JsonSchemaElementUtils.jsonSchemaElementFrom(
            parameter.type,
            parameter.parameterizedType,
            description,
            true,
            visited.toMutableMap()
        )
    }
}
