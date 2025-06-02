package dev.langchain4j.rag

import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.rag.query.Metadata

/**
 * Represents a request for [ChatMessage] augmentation.
 */
class AugmentationRequest(chatMessage: ChatMessage, metadata: Metadata) {
    /**
     * The chat message to be augmented.
     * Currently, only [UserMessage] is supported.
     */
    private val chatMessage: ChatMessage =
        chatMessage!!

    /**
     * Additional metadata related to the augmentation request.
     */
    private val metadata: Metadata =
        metadata!!

    fun chatMessage(): ChatMessage {
        return chatMessage
    }

    fun metadata(): Metadata {
        return metadata
    }
}
