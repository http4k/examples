package dev.langchain4j.model.moderation

import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.input.Prompt
import dev.langchain4j.model.output.Response

/**
 * Represents a model that can moderate text.
 */
interface ModerationModel {
    /**
     * Moderates the given text.
     * @param text the text to moderate.
     * @return the moderation `Response`.
     */
    fun moderate(text: String?): Response<Moderation>

    /**
     * Moderates the given prompt.
     * @param prompt the prompt to moderate.
     * @return the moderation `Response`.
     */
    fun moderate(prompt: Prompt): Response<Moderation> {
        return moderate(prompt.text())
    }

    /**
     * Moderates the given chat message.
     * @param message the chat message to moderate.
     * @return the moderation `Response`.
     */
    fun moderate(message: ChatMessage): Response<Moderation> {
        return moderate(java.util.List.of(message))
    }

    /**
     * Moderates the given list of chat messages.
     * @param messages the list of chat messages to moderate.
     * @return the moderation `Response`.
     */
    fun moderate(messages: List<ChatMessage>?): Response<Moderation>

    /**
     * Moderates the given text segment.
     * @param textSegment the text segment to moderate.
     * @return the moderation `Response`.
     */
    fun moderate(textSegment: TextSegment): Response<Moderation> {
        return moderate(textSegment.text())
    }
}
