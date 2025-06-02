package dev.langchain4j.model.chat

import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.UserMessage.Companion.from
import dev.langchain4j.model.ModelProvider
import dev.langchain4j.model.chat.listener.ChatModelListener
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.request.ChatRequestParameters
import dev.langchain4j.model.chat.response.ChatResponse
import java.util.concurrent.ConcurrentHashMap

/**
 * Represents a language model that has a chat API.
 *
 * @see StreamingChatModel
 */
interface ChatModel {
    /**
     * This is the main API to interact with the chat model.
     *
     * @param chatRequest a [ChatRequest], containing all the inputs to the LLM
     * @return a [ChatResponse], containing all the outputs from the LLM
     */
    fun chat(chatRequest: ChatRequest): ChatResponse {
        val finalChatRequest: ChatRequest = ChatRequest.Companion.builder()
            .messages(chatRequest.messages())
            .parameters(defaultRequestParameters().overrideWith(chatRequest.parameters()!!))
            .build()

        val listeners = listeners()
        val attributes: Map<Any, Any> = ConcurrentHashMap()

        ChatModelListenerUtils.onRequest(finalChatRequest, provider(), attributes, listeners)
        try {
            val chatResponse = doChat(finalChatRequest)
            ChatModelListenerUtils.onResponse(chatResponse, finalChatRequest, provider(), attributes, listeners)
            return chatResponse
        } catch (error: Exception) {
            ChatModelListenerUtils.onError(error, finalChatRequest, provider(), attributes, listeners)
            throw error
        }
    }

    fun doChat(chatRequest: ChatRequest?): ChatResponse {
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

    fun chat(userMessage: String?): String? {
        val chatRequest: ChatRequest = ChatRequest.Companion.builder()
            .messages(from(userMessage))
            .build()

        val chatResponse = chat(chatRequest)

        return chatResponse.aiMessage().text()
    }

    fun chat(vararg messages: ChatMessage?): ChatResponse {
        val chatRequest: ChatRequest = ChatRequest.Companion.builder()
            .messages(*messages)
            .build()

        return chat(chatRequest)
    }

    fun chat(messages: List<ChatMessage?>?): ChatResponse {
        val chatRequest: ChatRequest = ChatRequest.Companion.builder()
            .messages(messages)
            .build()

        return chat(chatRequest)
    }

    fun supportedCapabilities(): Set<Capability> {
        return setOf()
    }
}
