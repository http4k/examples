package dev.langchain4j.model.input.structured

/**
 * Represents a structured prompt.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class StructuredPrompt(
    /**
     * Prompt template can be defined in one line or multiple lines.
     * If the template is defined in multiple lines, the lines will be joined with a delimiter defined below.
     * @return the prompt template lines.
     */
    vararg val value: String,
    /**
     * The delimiter to join the lines of the prompt template.
     * @return the delimiter.
     */
    val delimiter: String = "\n"
) {
    /**
     * Utility class for [StructuredPrompt].
     */
    object Util {
        /**
         * Validates that the given object is annotated with [StructuredPrompt].
         * @param structuredPrompt the object to validate.
         * @return the annotation.
         */
        fun validateStructuredPrompt(structuredPrompt: Any): StructuredPrompt {

            val cls: Class<*> = structuredPrompt.javaClass

            arrayOf<Any?>(
                cls.name
            )
            return cls.getAnnotation(StructuredPrompt::class.java)!!
        }

        /**
         * Joins the lines of the prompt template.
         * @param structuredPrompt the structured prompt.
         * @return the joined prompt template.
         */
        fun join(structuredPrompt: StructuredPrompt): String {
            return java.lang.String.join(structuredPrompt.delimiter, *structuredPrompt.value)
        }
    }
}
