package dev.langchain4j.data.message

/**
 * Represents a chat message.
 * Used together with [ChatModel] and [StreamingChatModel].
 *
 * @see SystemMessage
 *
 * @see UserMessage
 *
 * @see AiMessage
 *
 * @see ToolExecutionResultMessage
 *
 * @see CustomMessage
 */
interface ChatMessage {
    /**
     * The type of the message.
     *
     * @return the type of the message
     */
    fun type(): ChatMessageType
}
