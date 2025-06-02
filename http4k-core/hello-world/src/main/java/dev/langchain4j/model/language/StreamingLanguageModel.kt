package dev.langchain4j.model.language

import dev.langchain4j.model.StreamingResponseHandler
import dev.langchain4j.model.input.Prompt

/**
 * Represents a language model that has a simple text interface (as opposed to a chat interface)
 * and can stream a response one token at a time.
 * It is recommended to use the [StreamingChatModel] instead,
 * as it offers more features.
 */
interface StreamingLanguageModel {
    /**
     * Generates a response from the model based on a prompt.
     *
     * @param prompt  The prompt.
     * @param handler The handler for streaming the response.
     */
    fun generate(prompt: String?, handler: StreamingResponseHandler<String?>?)

    /**
     * Generates a response from the model based on a prompt.
     *
     * @param prompt  The prompt.
     * @param handler The handler for streaming the response.
     */
    fun generate(prompt: Prompt, handler: StreamingResponseHandler<String?>?) {
        generate(prompt.text(), handler)
    }
}
