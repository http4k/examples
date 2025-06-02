package dev.langchain4j.rag

import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.rag.content.Content

/**
 * Represents the result of a [ChatMessage] augmentation.
 */
class AugmentationResult(chatMessage: ChatMessage?, contents: List<Content?>?) {
    /**
     * The augmented chat message.
     */
    private val chatMessage: ChatMessage

    /**
     * A list of content used to augment the original chat message.
     */
    private val contents: List<Content?>

    init {
        this.chatMessage = ValidationUtils.ensureNotNull(chatMessage, "chatMessage")
        this.contents = Utils.copy(contents)
    }

    fun chatMessage(): ChatMessage {
        return chatMessage
    }

    fun contents(): List<Content?> {
        return contents
    }

    class AugmentationResultBuilder internal constructor() {
        private var chatMessage: ChatMessage? = null
        private var contents: List<Content?>? = null

        fun chatMessage(chatMessage: ChatMessage?): AugmentationResultBuilder {
            this.chatMessage = chatMessage
            return this
        }

        fun contents(contents: List<Content?>?): AugmentationResultBuilder {
            this.contents = contents
            return this
        }

        fun build(): AugmentationResult {
            return AugmentationResult(this.chatMessage, this.contents)
        }
    }

    companion object {
        fun builder(): AugmentationResultBuilder {
            return AugmentationResultBuilder()
        }
    }
}
