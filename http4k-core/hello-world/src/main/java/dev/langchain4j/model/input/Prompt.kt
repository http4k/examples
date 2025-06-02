package dev.langchain4j.model.input

import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.AiMessage.Companion.aiMessage
import dev.langchain4j.data.message.SystemMessage
import dev.langchain4j.data.message.SystemMessage.Companion.systemMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.data.message.UserMessage.Companion.userMessage
import dev.langchain4j.internal.Utils
import java.util.Objects

/**
 * Represents a prompt (an input text sent to the LLM).
 * A prompt usually contains instructions, contextual information, end-user input, etc.
 * A Prompt is typically created by applying one or multiple values to a PromptTemplate.
 */
class Prompt(text: String?) {
    private val text: String = text!!

    /**
     * The text of the prompt.
     * @return the text of the prompt.
     */
    fun text(): String {
        return text
    }

    /**
     * Convert this prompt to a SystemMessage.
     * @return the SystemMessage.
     */
    fun toSystemMessage(): SystemMessage {
        return systemMessage(text)
    }

    /**
     * Convert this prompt to a UserMessage with specified userName.
     * @return the UserMessage.
     */
    fun toUserMessage(userName: String?): UserMessage {
        return userMessage(userName, text)
    }

    /**
     * Convert this prompt to a UserMessage.
     * @return the UserMessage.
     */
    fun toUserMessage(): UserMessage {
        return userMessage(text)
    }

    /**
     * Convert this prompt to an AiMessage.
     * @return the AiMessage.
     */
    fun toAiMessage(): AiMessage {
        return aiMessage(text)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Prompt
        return this.text == that.text
    }

    override fun hashCode(): Int {
        return Objects.hash(text)
    }

    override fun toString(): String {
        return "Prompt {" +
                " text = " + Utils.quoted(text) +
                " }"
    }

    companion object {
        /**
         * Create a new Prompt.
         * @param text the text of the prompt.
         * @return the new Prompt.
         */
        fun from(text: String?): Prompt {
            return Prompt(text)
        }
    }
}
