package dev.langchain4j.model.chat

import dev.langchain4j.model.ModelProvider
import dev.langchain4j.model.chat.listener.ChatModelErrorContext
import dev.langchain4j.model.chat.listener.ChatModelListener
import dev.langchain4j.model.chat.listener.ChatModelRequestContext
import dev.langchain4j.model.chat.listener.ChatModelResponseContext
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.response.ChatResponse
import java.util.function.Consumer

internal object ChatModelListenerUtils {
    fun onRequest(
        chatRequest: ChatRequest,
        modelProvider: ModelProvider,
        attributes: Map<Any, Any>,
        listeners: List<ChatModelListener>?
    ) {
        if (listeners == null || listeners.isEmpty()) {
            return
        }
        val requestContext = ChatModelRequestContext(chatRequest, modelProvider, attributes)
        listeners.forEach(Consumer { listener: ChatModelListener ->
            try {
                listener.onRequest(requestContext)
            } catch (e: Exception) {
            }
        })
    }

    fun onResponse(
        chatResponse: ChatResponse,
        chatRequest: ChatRequest,
        modelProvider: ModelProvider,
        attributes: Map<Any, Any>,
        listeners: List<ChatModelListener>?
    ) {
        if (listeners == null || listeners.isEmpty()) {
            return
        }
        val responseContext = ChatModelResponseContext(
            chatResponse, chatRequest, modelProvider, attributes
        )
        listeners.forEach(Consumer { listener: ChatModelListener ->
            try {
                listener.onResponse(responseContext)
            } catch (e: Exception) {
            }
        })
    }

    fun onError(
        error: Throwable,
        chatRequest: ChatRequest,
        modelProvider: ModelProvider,
        attributes: Map<Any, Any>,
        listeners: List<ChatModelListener>?
    ) {
        if (listeners == null || listeners.isEmpty()) {
            return
        }
        val errorContext = ChatModelErrorContext(error, chatRequest, modelProvider, attributes)
        listeners.forEach(Consumer { listener: ChatModelListener ->
            try {
                listener.onError(errorContext)
            } catch (e: Exception) {
            }
        })
    }
}
