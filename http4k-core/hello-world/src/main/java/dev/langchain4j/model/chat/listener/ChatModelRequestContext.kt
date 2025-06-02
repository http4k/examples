package dev.langchain4j.model.chat.listener

import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.model.ModelProvider
import dev.langchain4j.model.chat.request.ChatRequest

/**
 * The chat model request context.
 * It contains the [ChatRequest], [ModelProvider] and attributes.
 * The attributes can be used to pass data between methods of a [ChatModelListener]
 * or between multiple [ChatModelListener]s.
 */
class ChatModelRequestContext(
    chatRequest: ChatRequest,
    private val modelProvider: ModelProvider,
    attributes: Map<Any, Any>
) {
    private val chatRequest =
        ValidationUtils.ensureNotNull(chatRequest, "chatRequest")
    private val attributes =
        ValidationUtils.ensureNotNull(
            attributes,
            "attributes"
        )

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
