package dev.langchain4j.model.chat

import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.UserMessage.Companion.from
import dev.langchain4j.model.ModelProvider
import dev.langchain4j.model.chat.listener.ChatModelListener
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.request.ChatRequestParameters
import dev.langchain4j.model.chat.response.ChatResponse
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler
import java.util.concurrent.ConcurrentHashMap

/**
 * Represents a language model that has a chat API and can stream a response one token at a time.
 *
 * @see ChatModel
 */
interface StreamingChatModel {
    /**
     * This is the main API to interact with the chat model.
     *
     * @param chatRequest a [ChatRequest], containing all the inputs to the LLM
     * @param handler     a [StreamingChatResponseHandler] that will handle streaming response from the LLM
     */
    fun chat(chatRequest: ChatRequest, handler: StreamingChatResponseHandler) {
        val finalChatRequest: ChatRequest = ChatRequest.Companion.builder()
            .messages(chatRequest.messages())
            .parameters(defaultRequestParameters().overrideWith(chatRequest.parameters()!!))
            .build()

        val listeners = listeners()
        val attributes: Map<Any, Any> = ConcurrentHashMap()

        val observingHandler: StreamingChatResponseHandler = object : StreamingChatResponseHandler {
            override fun onPartialResponse(partialResponse: String?) {
                handler.onPartialResponse(partialResponse)
            }

            override fun onCompleteResponse(completeResponse: ChatResponse) {
                ChatModelListenerUtils.onResponse(completeResponse, finalChatRequest, provider(), attributes, listeners)
                handler.onCompleteResponse(completeResponse)
            }

            override fun onError(error: Throwable) {
                ChatModelListenerUtils.onError(error, finalChatRequest, provider(), attributes, listeners)
                handler.onError(error)
            }
        }

        ChatModelListenerUtils.onRequest(finalChatRequest, provider(), attributes, listeners)
        doChat(finalChatRequest, observingHandler)
    }

    fun doChat(chatRequest: ChatRequest?, handler: StreamingChatResponseHandler?) {
        throw RuntimeException("Not implemented")
    }

    fun defaultRequestParameters(): ChatRequestParameters {
        return ChatRequestParameters.Companion.builder().build()
    }

    fun listeners(): List<ChatModelListener> {
        return listOf()
    }

    fun provider(): ModelProvider {
        return ModelProvider.OTHER
    }

    fun chat(userMessage: String?, handler: StreamingChatResponseHandler) {
        val chatRequest: ChatRequest = ChatRequest.Companion.builder()
            .messages(from(userMessage))
            .build()

        chat(chatRequest, handler)
    }

    fun chat(messages: List<ChatMessage?>?, handler: StreamingChatResponseHandler) {
        val chatRequest: ChatRequest = ChatRequest.Companion.builder()
            .messages(messages)
            .build()

        chat(chatRequest, handler)
    }

    fun supportedCapabilities(): Set<Capability> {
        return setOf()
    }
}
