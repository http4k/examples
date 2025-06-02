package dev.langchain4j.model.chat.listener

import dev.langchain4j.model.ModelProvider
import dev.langchain4j.model.chat.request.ChatRequest

/**
 * The chat model error context.
 * It contains the error, corresponding [ChatRequest], [ModelProvider] and attributes.
 * The attributes can be used to pass data between methods of a [ChatModelListener]
 * or between multiple [ChatModelListener]s.
 */
class ChatModelErrorContext(
    error: Throwable,
    chatRequest: ChatRequest,
    private val modelProvider: ModelProvider,
    attributes: Map<Any, Any>
) {
    private val error =
        error!!
    private val chatRequest =
        chatRequest!!
    private val attributes =
        attributes!!

    /**
     * @return The error that occurred.
     */
    fun error(): Throwable {
        return error
    }

    fun chatRequest(): ChatRequest {
        return chatRequest
    }

    fun modelProvider(): ModelProvider {
        return modelProvider
    }

    /**
     * @return The attributes map. It can be used to pass data between methods of a [ChatModelListener]
     * or between multiple [ChatModelListener]s.
     */
    fun attributes(): Map<Any, Any> {
        return attributes
    }
}
