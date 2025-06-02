package dev.langchain4j.spi.prompt.structured

import dev.langchain4j.model.input.Prompt

/**
 * Represents a factory for structured prompts.
 */
interface StructuredPromptFactory {
    /**
     * Converts the given structured prompt to a prompt.
     * @param structuredPrompt the structured prompt.
     * @return the prompt.
     */
    fun toPrompt(structuredPrompt: Any?): Prompt?
}
