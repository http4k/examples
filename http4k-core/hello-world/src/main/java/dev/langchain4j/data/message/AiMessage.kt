package dev.langchain4j.data.message

import dev.langchain4j.agent.tool.ToolExecutionRequest
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Arrays
import java.util.Objects

/**
 * Represents a response message from an AI (language model).
 * The message can contain either a textual response or a request to execute one/multiple tool(s).
 * In the case of tool execution, the response to this message should be one/multiple [ToolExecutionResultMessage].
 */
class AiMessage : ChatMessage {
    private val text: String?
    private val toolExecutionRequests: List<ToolExecutionRequest?>

    /**
     * Create a new [AiMessage] with the given text.
     *
     * @param text the text of the message.
     */
    constructor(text: String) {
        this.text = ValidationUtils.ensureNotNull(text, "text")
        this.toolExecutionRequests = listOf<ToolExecutionRequest>()
    }

    /**
     * Create a new [AiMessage] with the given tool execution requests.
     *
     * @param toolExecutionRequests the tool execution requests of the message.
     */
    constructor(toolExecutionRequests: List<ToolExecutionRequest?>) {
        this.text = null
        this.toolExecutionRequests = ValidationUtils.ensureNotEmpty(toolExecutionRequests, "toolExecutionRequests")
    }

    /**
     * Create a new [AiMessage] with the given text and tool execution requests.
     *
     * @param text                  the text of the message.
     * @param toolExecutionRequests the tool execution requests of the message.
     */
    constructor(text: String?, toolExecutionRequests: List<ToolExecutionRequest?>?) {
        this.text = text
        this.toolExecutionRequests = Utils.copy(toolExecutionRequests)
    }

    /**
     * Get the text of the message.
     *
     * @return the text of the message.
     */
    fun text(): String? {
        return text
    }

    /**
     * Get the tool execution requests of the message.
     *
     * @return the tool execution requests of the message.
     */
    fun toolExecutionRequests(): List<ToolExecutionRequest?> {
        return toolExecutionRequests
    }

    /**
     * Check if the message has ToolExecutionRequests.
     *
     * @return true if the message has ToolExecutionRequests, false otherwise.
     */
    fun hasToolExecutionRequests(): Boolean {
        return !Utils.isNullOrEmpty(toolExecutionRequests)
    }

    override fun type(): ChatMessageType {
        return ChatMessageType.AI
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as AiMessage
        return this.text == that.text
                && this.toolExecutionRequests == that.toolExecutionRequests
    }

    override fun hashCode(): Int {
        return Objects.hash(text, toolExecutionRequests)
    }

    override fun toString(): String {
        return "AiMessage {" +
                " text = " + Utils.quoted(text) +
                " toolExecutionRequests = " + toolExecutionRequests +
                " }"
    }

    class Builder {
        private var text: String? = null
        private var toolExecutionRequests: List<ToolExecutionRequest?>? = null

        fun text(text: String?): Builder {
            this.text = text
            return this
        }

        fun toolExecutionRequests(toolExecutionRequests: List<ToolExecutionRequest?>?): Builder {
            this.toolExecutionRequests = toolExecutionRequests
            return this
        }

        fun build(): AiMessage {
            return AiMessage(text, toolExecutionRequests)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }

        /**
         * Create a new [AiMessage] with the given text.
         *
         * @param text the text of the message.
         * @return the new [AiMessage].
         */
        fun from(text: String): AiMessage {
            return AiMessage(text)
        }

        /**
         * Create a new [AiMessage] with the given tool execution requests.
         *
         * @param toolExecutionRequests the tool execution requests of the message.
         * @return the new [AiMessage].
         */
        fun from(vararg toolExecutionRequests: ToolExecutionRequest?): AiMessage {
            return from(Arrays.asList(*toolExecutionRequests))
        }

        /**
         * Create a new [AiMessage] with the given tool execution requests.
         *
         * @param toolExecutionRequests the tool execution requests of the message.
         * @return the new [AiMessage].
         */
        fun from(toolExecutionRequests: List<ToolExecutionRequest?>): AiMessage {
            return AiMessage(toolExecutionRequests)
        }

        /**
         * Create a new [AiMessage] with the given text and tool execution requests.
         *
         * @param text                  the text of the message.
         * @param toolExecutionRequests the tool execution requests of the message.
         * @return the new [AiMessage].
         */
        fun from(text: String?, toolExecutionRequests: List<ToolExecutionRequest?>?): AiMessage {
            return AiMessage(text, toolExecutionRequests)
        }

        /**
         * Create a new [AiMessage] with the given text.
         *
         * @param text the text of the message.
         * @return the new [AiMessage].
         */
        @JvmStatic
        fun aiMessage(text: String): AiMessage {
            return from(text)
        }

        /**
         * Create a new [AiMessage] with the given tool execution requests.
         *
         * @param toolExecutionRequests the tool execution requests of the message.
         * @return the new [AiMessage].
         */
        @JvmStatic
        fun aiMessage(vararg toolExecutionRequests: ToolExecutionRequest?): AiMessage {
            return aiMessage(Arrays.asList(*toolExecutionRequests))
        }

        /**
         * Create a new [AiMessage] with the given tool execution requests.
         *
         * @param toolExecutionRequests the tool execution requests of the message.
         * @return the new [AiMessage].
         */
        fun aiMessage(toolExecutionRequests: List<ToolExecutionRequest?>): AiMessage {
            return from(toolExecutionRequests)
        }

        /**
         * Create a new [AiMessage] with the given text and tool execution requests.
         *
         * @param text                  the text of the message.
         * @param toolExecutionRequests the tool execution requests of the message.
         * @return the new [AiMessage].
         */
        fun aiMessage(text: String?, toolExecutionRequests: List<ToolExecutionRequest?>?): AiMessage {
            return from(text, toolExecutionRequests)
        }
    }
}
