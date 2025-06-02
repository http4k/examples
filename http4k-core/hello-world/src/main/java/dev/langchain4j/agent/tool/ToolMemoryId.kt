package dev.langchain4j.agent.tool

/**
 * If a [Tool] method parameter is annotated with this annotation,
 * memory id (parameter annotated with @MemoryId in AI Service) will be injected automatically.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class ToolMemoryId 
