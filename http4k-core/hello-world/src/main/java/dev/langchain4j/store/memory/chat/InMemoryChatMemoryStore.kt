package dev.langchain4j.store.memory.chat

import dev.langchain4j.data.message.ChatMessage
import java.util.concurrent.ConcurrentHashMap

/**
 * Implementation of [ChatMemoryStore] that stores state of [dev.langchain4j.memory.ChatMemory] (chat messages) in-memory.
 *
 *
 * This storage mechanism is transient and does not persist data across application restarts.
 */
class InMemoryChatMemoryStore
/**
 * Constructs a new [InMemoryChatMemoryStore].
 */
    : ChatMemoryStore {
    private val messagesByMemoryId: MutableMap<Any, List<ChatMessage>> = ConcurrentHashMap()

    override fun getMessages(memoryId: Any): List<ChatMessage> {
        return messagesByMemoryId.computeIfAbsent(memoryId) { ignored: Any? -> ArrayList() }
    }

    override fun updateMessages(memoryId: Any, messages: List<ChatMessage>) {
        messagesByMemoryId[memoryId] = messages
    }

    override fun deleteMessages(memoryId: Any) {
        messagesByMemoryId.remove(memoryId)
    }
}
