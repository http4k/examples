package dev.langchain4j.model.input.structured

import dev.langchain4j.model.input.Prompt
import dev.langchain4j.spi.ServiceHelper
import dev.langchain4j.spi.prompt.structured.StructuredPromptFactory

/**
 * Utility class for structured prompts.
 * Loads the [StructuredPromptFactory] SPI.
 */
object StructuredPromptProcessor {
    private val FACTORY = factory()

    private fun factory(): StructuredPromptFactory {
        for (factory in ServiceHelper.loadFactories(
            StructuredPromptFactory::class.java
        )) {
            return factory
        }
        TODO()
//        return StructuredPromptFactory { null }
    }

    /**
     * Converts the given structured prompt to a prompt.
     *
     * @param structuredPrompt the structured prompt.
     * @return the prompt.
     */
    fun toPrompt(structuredPrompt: Any?): Prompt {
        TODO()
//        return FACTORY.toPrompt(structuredPrompt)
    }
}
