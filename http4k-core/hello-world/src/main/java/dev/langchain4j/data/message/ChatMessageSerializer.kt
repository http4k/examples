package dev.langchain4j.data.message

import dev.langchain4j.spi.ServiceHelper
import dev.langchain4j.spi.data.message.ChatMessageJsonCodecFactory

object ChatMessageSerializer {
    val CODEC: ChatMessageJsonCodec = loadCodec()

    private fun loadCodec(): ChatMessageJsonCodec {
        for (factory in ServiceHelper.loadFactories(
            ChatMessageJsonCodecFactory::class.java
        )) {
            return factory.create()
        }
        return object : ChatMessageJsonCodec {
            override fun messageFromJson(json: String?): ChatMessage? {
                return null
            }

            override fun messagesFromJson(json: String?): List<ChatMessage> {
                return listOf()
            }

            override fun messageToJson(message: ChatMessage?): String {
                return ""
            }

            override fun messagesToJson(messages: List<ChatMessage?>?): String {
                return ""
            }
        }
    }

    /**
     * Serializes a chat message into a JSON string.
     *
     * @param message Chat message to be serialized.
     * @return A JSON string with the contents of the message.
     * @see ChatMessageDeserializer For details on deserialization.
     */
    fun messageToJson(message: ChatMessage?): String? {
        return CODEC.messageToJson(message)
    }

    /**
     * Serializes a list of chat messages into a JSON string.
     *
     * @param messages The list of chat messages to be serialized.
     * @return A JSON string representing provided chat messages.
     * @see ChatMessageDeserializer For details on deserialization.
     */
    fun messagesToJson(messages: List<ChatMessage?>?): String? {
        return CODEC.messagesToJson(messages)
    }
}
