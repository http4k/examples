package dev.langchain4j.data.message

import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Objects

/**
 * Represents a system message, typically defined by a developer.
 * This type of message usually provides instructions regarding the AI's actions, such as its behavior or response style.
 */
class SystemMessage(text: String?) : ChatMessage {
    private val text: String = ValidationUtils.ensureNotBlank(text, "text")

    /**
     * Returns the message text.
     * @return the message text.
     */
    fun text(): String {
        return text
    }

    override fun type(): ChatMessageType {
        return ChatMessageType.SYSTEM
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as SystemMessage
        return this.text == that.text
    }

    override fun hashCode(): Int {
        return Objects.hash(text)
    }

    override fun toString(): String {
        return "SystemMessage {" +
                " text = " + Utils.quoted(text) +
                " }"
    }

    companion object {
        /**
         * Creates a new system message.
         * @param text the message text.
         * @return the system message.
         */
        fun from(text: String?): SystemMessage {
            return SystemMessage(text)
        }

        /**
         * Creates a new system message.
         * @param text the message text.
         * @return the system message.
         */
        @JvmStatic
        fun systemMessage(text: String?): SystemMessage {
            return from(text)
        }
    }
}
