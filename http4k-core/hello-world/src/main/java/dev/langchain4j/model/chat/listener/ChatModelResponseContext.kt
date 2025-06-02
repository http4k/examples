package dev.langchain4j.model.chat.listener

import dev.langchain4j.model.ModelProvider
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.response.ChatResponse

/**
 * The chat response context.
 * It contains [ChatResponse], corresponding [ChatRequest], [ModelProvider] and attributes.
 * The attributes can be used to pass data between methods of a [ChatModelListener]
 * or between multiple [ChatModelListener]s.
 */
class ChatModelResponseContext(
    chatResponse: ChatResponse,
    chatRequest: ChatRequest,
    private val modelProvider: ModelProvider,
    attributes: Map<Any, Any>
) {
    private val chatResponse =
        chatResponse!!
    private val chatRequest =
        chatRequest!!
    private val attributes =
        attributes!!

    fun chatResponse(): ChatResponse {
        return chatResponse
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
