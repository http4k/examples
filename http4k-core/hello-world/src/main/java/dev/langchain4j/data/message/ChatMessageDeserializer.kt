package dev.langchain4j.data.message

/**
 * A deserializer for [ChatMessage] objects.
 */
object ChatMessageDeserializer {
    /**
     * Deserializes a JSON string into a [ChatMessage].
     *
     * @param json The JSON string representing a chat message.
     * @return A [ChatMessage] deserialized from the provided JSON string.
     * @see ChatMessageSerializer For details on serialization.
     */
    fun messageFromJson(json: String?): ChatMessage? {
        return ChatMessageSerializer.CODEC.messageFromJson(json)
    }

    /**
     * Deserializes a JSON string into a list of [ChatMessage].
     *
     * @param json The JSON string containing an array of chat messages.
     * @return A list of [ChatMessage] deserialized from the provided JSON string.
     * @see ChatMessageSerializer For details on serialization.
     */
    fun messagesFromJson(json: String?): List<ChatMessage?>? {
        return ChatMessageSerializer.CODEC.messagesFromJson(json)
    }
}
