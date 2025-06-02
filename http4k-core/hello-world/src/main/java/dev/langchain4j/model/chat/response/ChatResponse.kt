package dev.langchain4j.model.chat.response

import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.model.output.FinishReason
import dev.langchain4j.model.output.TokenUsage
import java.util.Objects

class ChatResponse protected constructor(builder: Builder) {
    private val aiMessage: AiMessage
    private val metadata: ChatResponseMetadata?

    init {
        this.aiMessage = builder.aiMessage!!

        val metadataBuilder: ChatResponseMetadata.Builder<*> = ChatResponseMetadata.Companion.builder()
        if (builder.id != null) {
            validate(builder, "id")
            metadataBuilder.id(builder.id)
        }
        if (builder.modelName != null) {
            validate(builder, "modelName")
            metadataBuilder.modelName(builder.modelName)
        }
        if (builder.tokenUsage != null) {
            validate(builder, "tokenUsage")
            metadataBuilder.tokenUsage(builder.tokenUsage)
        }
        if (builder.finishReason != null) {
            validate(builder, "finishReason")
            metadataBuilder.finishReason(builder.finishReason)
        }
        if (builder.metadata != null) {
            this.metadata = builder.metadata
        } else {
            this.metadata = metadataBuilder.build()
        }
    }

    fun aiMessage(): AiMessage {
        return aiMessage
    }

    fun metadata(): ChatResponseMetadata? {
        return metadata
    }

    fun id(): String? {
        return metadata!!.id()
    }

    fun modelName(): String? {
        return metadata!!.modelName()
    }

    fun tokenUsage(): TokenUsage? {
        return metadata!!.tokenUsage()
    }

    fun finishReason(): FinishReason? {
        return metadata!!.finishReason()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ChatResponse
        return this.aiMessage == that.aiMessage
                && this.metadata == that.metadata
    }

    override fun hashCode(): Int {
        return Objects.hash(aiMessage, metadata)
    }

    override fun toString(): String {
        return "ChatResponse {" +
                " aiMessage = " + aiMessage +
                ", metadata = " + metadata +
                " }"
    }

    class Builder {
        var aiMessage: AiMessage? = null
        var metadata: ChatResponseMetadata? = null

        var id: String? = null
        var modelName: String? = null
        var tokenUsage: TokenUsage? = null
        var finishReason: FinishReason? = null

        fun aiMessage(aiMessage: AiMessage?): Builder {
            this.aiMessage = aiMessage
            return this
        }

        fun metadata(metadata: ChatResponseMetadata?): Builder {
            this.metadata = metadata
            return this
        }

        fun id(id: String?): Builder {
            this.id = id
            return this
        }

        fun modelName(modelName: String?): Builder {
            this.modelName = modelName
            return this
        }

        fun tokenUsage(tokenUsage: TokenUsage?): Builder {
            this.tokenUsage = tokenUsage
            return this
        }

        fun finishReason(finishReason: FinishReason?): Builder {
            this.finishReason = finishReason
            return this
        }

        fun build(): ChatResponse {
            return ChatResponse(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }

        private fun validate(builder: Builder, name: String) {
            require(builder.metadata == null) { "Cannot set both 'metadata' and '%s' on ChatResponse" }
        }
    }
}
