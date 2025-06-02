package dev.langchain4j.data.message

/**
 * A codec for serializing and deserializing [ChatMessage] objects to and from JSON.
 */
interface ChatMessageJsonCodec {
    /**
     * Deserializes a JSON string to a [ChatMessage] object.
     * @param json the JSON string.
     * @return the deserialized [ChatMessage] object.
     */
    fun messageFromJson(json: String?): ChatMessage?

    /**
     * Deserializes a JSON string to a list of [ChatMessage] objects.
     * @param json the JSON string.
     * @return the deserialized list of [ChatMessage] objects.
     */
    fun messagesFromJson(json: String?): List<ChatMessage>

    /**
     * Serializes a [ChatMessage] object to a JSON string.
     * @param message the [ChatMessage] object.
     * @return the serialized JSON string.
     */
    fun messageToJson(message: ChatMessage?): String

    /**
     * Serializes a list of [ChatMessage] objects to a JSON string.
     * @param messages the list of [ChatMessage] objects.
     * @return the serialized JSON string.
     */
    fun messagesToJson(messages: List<ChatMessage?>?): String
}
