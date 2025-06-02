package dev.langchain4j.model.chat

import dev.langchain4j.model.ModelDisabledException
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler

/**
 * A [StreamingChatModel] which throws a [ModelDisabledException] for all of its methods
 *
 *
 * This could be used in tests, or in libraries that extend this one to conditionally enable or disable functionality.
 *
 */
class DisabledStreamingChatModel : StreamingChatModel {
    override fun doChat(chatRequest: ChatRequest?, handler: StreamingChatResponseHandler?) {
        throw ModelDisabledException("StreamingChatModel is disabled")
    }
}
