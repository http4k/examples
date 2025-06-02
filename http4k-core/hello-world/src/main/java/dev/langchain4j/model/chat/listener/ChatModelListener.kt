package dev.langchain4j.model.chat.listener

import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.response.ChatResponse

/**
 * A [ChatModel] listener that listens for requests, responses and errors.
 */
interface ChatModelListener {
    /**
     * This method is called before the request is sent to the model.
     *
     * @param requestContext The request context. It contains the [ChatRequest] and attributes.
     * The attributes can be used to pass data between methods of this listener
     * or between multiple listeners.
     */
    fun onRequest(requestContext: ChatModelRequestContext?) {
    }

    /**
     * This method is called after the response is received from the model.
     *
     * @param responseContext The response context.
     * It contains [ChatResponse], corresponding [ChatRequest] and attributes.
     * The attributes can be used to pass data between methods of this listener
     * or between multiple listeners.
     */
    fun onResponse(responseContext: ChatModelResponseContext?) {
    }

    /**
     * This method is called when an error occurs during interaction with the model.
     *
     * @param errorContext The error context.
     * It contains the error, corresponding [ChatRequest],
     * partial [ChatResponse] (if available) and attributes.
     * The attributes can be used to pass data between methods of this listener
     * or between multiple listeners.
     */
    fun onError(errorContext: ChatModelErrorContext?) {
    }
}
