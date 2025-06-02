package dev.langchain4j.data.message

import dev.langchain4j.data.message.AiMessage

/**
 * The type of content, e.g. text or image.
 * Maps to implementations of [ChatMessage].
 */
enum class ChatMessageType(private val messageClass: Class<out ChatMessage>) {
    /**
     * A message from the system, typically defined by a developer.
     */
    SYSTEM(SystemMessage::class.java),

    /**
     * A message from the user.
     */
    USER(UserMessage::class.java),

    /**
     * A message from the AI.
     */
    AI(AiMessage::class.java),

    /**
     * A message from a tool.
     */
    TOOL_EXECUTION_RESULT(ToolExecutionResultMessage::class.java),

    /**
     * A custom message.
     */
    CUSTOM(CustomMessage::class.java);

    /**
     * Returns the class of the message type.
     * @return the class of the message type.
     */
    fun messageClass(): Class<out ChatMessage> {
        return messageClass
    }
}
