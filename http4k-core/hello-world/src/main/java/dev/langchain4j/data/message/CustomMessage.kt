package dev.langchain4j.data.message

import dev.langchain4j.internal.Utils
import java.util.Objects

/**
 * Represents a custom message.
 * Can be used only with [ChatModel] implementations that support this type of message.
 */
class CustomMessage(attributes: Map<String, Any>?) : ChatMessage {
    private val attributes: Map<String, Any> =
        Utils.copy(attributes)

    /**
     * Returns the message attributes.
     *
     * @return the message attributes.
     */
    fun attributes(): Map<String, Any> {
        return attributes
    }

    override fun type(): ChatMessageType {
        return ChatMessageType.CUSTOM
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as CustomMessage
        return this.attributes == that.attributes
    }

    override fun hashCode(): Int {
        return Objects.hash(attributes)
    }

    override fun toString(): String {
        return "CustomMessage { attributes = $attributes }"
    }

    companion object {
        /**
         * Creates a new custom message.
         *
         * @param attributes the message attributes.
         * @return the custom message.
         */
        fun from(attributes: Map<String, Any>?): CustomMessage {
            return CustomMessage(attributes)
        }

        /**
         * Creates a new custom message.
         *
         * @param attributes the message attributes.
         * @return the custom message.
         */
        fun customMessage(attributes: Map<String, Any>?): CustomMessage {
            return from(attributes)
        }
    }
}
