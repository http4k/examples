package dev.langchain4j.model.moderation

import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.ModelDisabledException
import dev.langchain4j.model.input.Prompt
import dev.langchain4j.model.output.Response

/**
 * A [ModerationModel] which throws a [ModelDisabledException] for all of its methods
 *
 *
 * This could be used in tests, or in libraries that extend this one to conditionally enable or disable functionality.
 *
 */
class DisabledModerationModel : ModerationModel {
    override fun moderate(text: String?): Response<Moderation> {
        throw ModelDisabledException("ModerationModel is disabled")
    }

    override fun moderate(prompt: Prompt): Response<Moderation> {
        throw ModelDisabledException("ModerationModel is disabled")
    }

    override fun moderate(message: ChatMessage): Response<Moderation> {
        throw ModelDisabledException("ModerationModel is disabled")
    }

    override fun moderate(messages: List<ChatMessage>?): Response<Moderation> {
        throw ModelDisabledException("ModerationModel is disabled")
    }

    override fun moderate(textSegment: TextSegment): Response<Moderation> {
        throw ModelDisabledException("ModerationModel is disabled")
    }
}
