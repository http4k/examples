package dev.langchain4j.agent.tool

/**
 * Parameter of a Tool
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class P(
    /**
     * Description of a parameter
     * @return the description of a parameter
     */
    val value: String,
    /**
     * Whether the parameter is required
     * @return true if the parameter is required, false otherwise
     * Default is true.
     */
    val required: Boolean = true
)
