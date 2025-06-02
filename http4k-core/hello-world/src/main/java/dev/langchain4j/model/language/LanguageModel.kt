package dev.langchain4j.model.language

import dev.langchain4j.model.input.Prompt
import dev.langchain4j.model.output.Response

/**
 * Represents a language model that has a simple text interface (as opposed to a chat interface).
 * It is recommended to use the [ChatModel] instead,
 * as it offers more features.
 */
interface LanguageModel {
    /**
     * Generate a response to the given prompt.
     *
     * @param prompt the prompt.
     * @return the response.
     */
    fun generate(prompt: String?): Response<String>

    /**
     * Generate a response to the given prompt.
     *
     * @param prompt the prompt.
     * @return the response.
     */
    fun generate(prompt: Prompt): Response<String> {
        return generate(prompt.text())
    }
}
