package dev.langchain4j.model.chat.response

import dev.langchain4j.model.output.FinishReason
import dev.langchain4j.model.output.TokenUsage
import java.util.Objects

/**
 * Represents common chat response metadata supported by most LLM providers.
 * Specific LLM provider integrations can extend this interface to add provider-specific metadata.
 */
class ChatResponseMetadata protected constructor(builder: Builder<*>) {
    private val id: String?
    private val modelName: String?
    private val tokenUsage: TokenUsage?
    private val finishReason: FinishReason?

    init {
        this.id = builder.id
        this.modelName = builder.modelName
        this.tokenUsage = builder.tokenUsage
        this.finishReason = builder.finishReason
    }

    fun id(): String? {
        return id
    }

    fun modelName(): String? {
        return modelName
    }

    fun tokenUsage(): TokenUsage? {
        return tokenUsage
    }

    fun finishReason(): FinishReason? {
        return finishReason
    }

    fun toBuilder(): Builder<*> {
        return toBuilder(builder())
    }

    protected fun toBuilder(builder: Builder<*>): Builder<*> {
        return builder
            .id(id)!!
            .modelName(modelName)!!
            .tokenUsage(tokenUsage)!!
            .finishReason(finishReason)!!
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ChatResponseMetadata
        return id == that.id
                && modelName == that.modelName
                && tokenUsage == that.tokenUsage
                && finishReason == that.finishReason
    }

    override fun hashCode(): Int {
        return Objects.hash(id, modelName, tokenUsage, finishReason)
    }

    override fun toString(): String {
        return "ChatResponseMetadata{" +
                "id='" + id + '\'' +
                ", modelName='" + modelName + '\'' +
                ", tokenUsage=" + tokenUsage +
                ", finishReason=" + finishReason +
                '}'
    }

    class Builder<T : Builder<T>?> {
        var id: String? = null
        var modelName: String? = null
        var tokenUsage: TokenUsage? = null
        var finishReason: FinishReason? = null

        fun id(id: String?): T {
            this.id = id
            return this as T
        }

        fun modelName(modelName: String?): T {
            this.modelName = modelName
            return this as T
        }

        fun tokenUsage(tokenUsage: TokenUsage?): T {
            this.tokenUsage = tokenUsage
            return this as T
        }

        fun finishReason(finishReason: FinishReason?): T {
            this.finishReason = finishReason
            return this as T
        }

        fun build(): ChatResponseMetadata {
            return ChatResponseMetadata(this)
        }
    }

    companion object {
        fun builder(): Builder<*> {
            TODO()
//            return Builder<T>()
        }
    }
}
