package dev.langchain4j.rag.query

import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.rag.RetrievalAugmentor
import java.util.Objects

/**
 * Represents metadata that may be useful or necessary for retrieval or augmentation purposes.
 */
class Metadata(chatMessage: ChatMessage, private val chatMemoryId: Any, chatMemory: List<ChatMessage>?) {
    private val chatMessage: ChatMessage =
        ValidationUtils.ensureNotNull(chatMessage, "chatMessage")
    private val chatMemory: List<ChatMessage> =
        Utils.copy(chatMemory)

    /**
     * @return an original [ChatMessage] passed to the [RetrievalAugmentor.augment].
     */
    fun chatMessage(): ChatMessage {
        return chatMessage
    }

    /**
     * @return a chat memory ID. Present when [ChatMemory] is used. Can be used to distinguish between users.
     * See `@dev.langchain4j.service.MemoryId` annotation from a `dev.langchain4j:langchain4j` module.
     */
    fun chatMemoryId(): Any {
        return chatMemoryId
    }

    /**
     * @return previous messages in the [ChatMemory]. Present when [ChatMemory] is used.
     * Can be used to get more details about the context (conversation) in which the [Query] originated.
     */
    fun chatMemory(): List<ChatMessage> {
        return chatMemory
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Metadata
        return this.chatMessage == that.chatMessage
                && this.chatMemoryId == that.chatMemoryId
                && this.chatMemory == that.chatMemory
    }

    override fun hashCode(): Int {
        return Objects.hash(chatMessage, chatMemoryId, chatMemory)
    }

    override fun toString(): String {
        return "Metadata {" +
                " chatMessage = " + chatMessage +
                ", chatMemoryId = " + chatMemoryId +
                ", chatMemory = " + chatMemory +
                " }"
    }

    companion object {
        fun from(chatMessage: ChatMessage, chatMemoryId: Any, chatMemory: List<ChatMessage>?): Metadata {
            return Metadata(chatMessage, chatMemoryId, chatMemory)
        }
    }
}
