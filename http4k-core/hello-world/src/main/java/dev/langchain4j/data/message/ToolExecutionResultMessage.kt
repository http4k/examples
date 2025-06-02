package dev.langchain4j.data.message

import dev.langchain4j.agent.tool.ToolExecutionRequest
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Objects

/**
 * Represents the result of a tool execution in response to a [ToolExecutionRequest].
 * [ToolExecutionRequest]s come from a previous [AiMessage.toolExecutionRequests].
 */
class ToolExecutionResultMessage(private val id: String?, private val toolName: String?, text: String?) : ChatMessage {
    private val text: String = ValidationUtils.ensureNotBlank(text, "text")

    /**
     * Returns the id of the tool.
     * @return the id of the tool.
     */
    fun id(): String? {
        return id
    }

    /**
     * Returns the name of the tool.
     * @return the name of the tool.
     */
    fun toolName(): String? {
        return toolName
    }

    /**
     * Returns the result of the tool execution.
     * @return the result of the tool execution.
     */
    fun text(): String {
        return text
    }

    override fun type(): ChatMessageType {
        return ChatMessageType.TOOL_EXECUTION_RESULT
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ToolExecutionResultMessage
        return this.id == that.id
                && this.toolName == that.toolName
                && this.text == that.text
    }

    override fun hashCode(): Int {
        return Objects.hash(id, toolName, text)
    }

    override fun toString(): String {
        return "ToolExecutionResultMessage {" +
                " id = " + Utils.quoted(id) +
                " toolName = " + Utils.quoted(toolName) +
                " text = " + Utils.quoted(text) +
                " }"
    }

    companion object {
        /**
         * Creates a [ToolExecutionResultMessage] from a [ToolExecutionRequest] and the result of the tool execution.
         * @param request the request.
         * @param toolExecutionResult the result of the tool execution.
         * @return the [ToolExecutionResultMessage].
         */
        fun from(request: ToolExecutionRequest, toolExecutionResult: String?): ToolExecutionResultMessage {
            return ToolExecutionResultMessage(request.id(), request.name(), toolExecutionResult)
        }

        /**
         * Creates a [ToolExecutionResultMessage] from a [ToolExecutionRequest] and the result of the tool execution.
         * @param id the id of the tool.
         * @param toolName the name of the tool.
         * @param toolExecutionResult the result of the tool execution.
         * @return the [ToolExecutionResultMessage].
         */
        fun from(id: String?, toolName: String?, toolExecutionResult: String?): ToolExecutionResultMessage {
            return ToolExecutionResultMessage(id, toolName, toolExecutionResult)
        }

        /**
         * Creates a [ToolExecutionResultMessage] from a [ToolExecutionRequest] and the result of the tool execution.
         * @param request the request.
         * @param toolExecutionResult the result of the tool execution.
         * @return the [ToolExecutionResultMessage].
         */
        fun toolExecutionResultMessage(
            request: ToolExecutionRequest,
            toolExecutionResult: String?
        ): ToolExecutionResultMessage {
            return from(request, toolExecutionResult)
        }

        /**
         * Creates a [ToolExecutionResultMessage] from a [ToolExecutionRequest] and the result of the tool execution.
         * @param id the id of the tool.
         * @param toolName the name of the tool.
         * @param toolExecutionResult the result of the tool execution.
         * @return the [ToolExecutionResultMessage].
         */
        fun toolExecutionResultMessage(
            id: String?,
            toolName: String?,
            toolExecutionResult: String?
        ): ToolExecutionResultMessage {
            return from(id, toolName, toolExecutionResult)
        }
    }
}
