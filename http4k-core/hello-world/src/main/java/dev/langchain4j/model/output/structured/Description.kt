package dev.langchain4j.model.output.structured

/**
 * Annotation to attach a description to a class field.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Description(
    /**
     * The description can be defined in one line or multiple lines.
     * If the description is defined in multiple lines, the lines will be joined with a space (" ") automatically.
     *
     * @return The description.
     */
    vararg val value: String
)
