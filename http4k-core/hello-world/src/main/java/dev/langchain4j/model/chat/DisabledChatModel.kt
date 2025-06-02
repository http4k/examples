package dev.langchain4j.model.chat

import dev.langchain4j.model.ModelDisabledException
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.response.ChatResponse

/**
 * A [ChatModel] which throws a [ModelDisabledException] for all of its methods
 *
 *
 * This could be used in tests, or in libraries that extend this one to conditionally enable or disable functionality.
 *
 */
class DisabledChatModel : ChatModel {
    override fun doChat(chatRequest: ChatRequest?): ChatResponse {
        throw ModelDisabledException("ChatModel is disabled")
    }
}
