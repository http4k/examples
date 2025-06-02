package dev.langchain4j.model.chat.request

import dev.langchain4j.agent.tool.ToolSpecification
import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Arrays
import java.util.Objects

class ChatRequest protected constructor(builder: Builder) {
    private val messages: List<ChatMessage?>
    private val parameters: ChatRequestParameters?

    init {
        this.messages = Utils.copy(ValidationUtils.ensureNotEmpty(builder.messages, "messages"))

        val parametersBuilder: DefaultChatRequestParameters.Builder<*> = ChatRequestParameters.Companion.builder()

        if (builder.modelName != null) {
            validate(builder, "modelName")
            parametersBuilder.modelName(builder.modelName)
        }
        if (builder.temperature != null) {
            validate(builder, "temperature")
            parametersBuilder.temperature(builder.temperature)
        }
        if (builder.topP != null) {
            validate(builder, "topP")
            parametersBuilder.topP(builder.topP)
        }
        if (builder.topK != null) {
            validate(builder, "topK")
            parametersBuilder.topK(builder.topK)
        }
        if (builder.frequencyPenalty != null) {
            validate(builder, "frequencyPenalty")
            parametersBuilder.frequencyPenalty(builder.frequencyPenalty)
        }
        if (builder.presencePenalty != null) {
            validate(builder, "presencePenalty")
            parametersBuilder.presencePenalty(builder.presencePenalty)
        }
        if (builder.maxOutputTokens != null) {
            validate(builder, "maxOutputTokens")
            parametersBuilder.maxOutputTokens(builder.maxOutputTokens)
        }
        if (!Utils.isNullOrEmpty(builder.stopSequences)) {
            validate(builder, "stopSequences")
            parametersBuilder.stopSequences(builder.stopSequences)
        }
        if (!Utils.isNullOrEmpty(builder.toolSpecifications)) {
            validate(builder, "toolSpecifications")
            parametersBuilder.toolSpecifications(builder.toolSpecifications)
        }
        if (builder.toolChoice != null) {
            validate(builder, "toolChoice")
            parametersBuilder.toolChoice(builder.toolChoice)
        }
        if (builder.responseFormat != null) {
            validate(builder, "responseFormat")
            parametersBuilder.responseFormat(builder.responseFormat)
        }

        if (builder.parameters != null) {
            this.parameters = builder.parameters
        } else {
            this.parameters = parametersBuilder.build()
        }
    }

    fun messages(): List<ChatMessage?> {
        return messages
    }

    fun parameters(): ChatRequestParameters? {
        return parameters
    }

    fun modelName(): String? {
        return parameters!!.modelName()
    }

    fun temperature(): Double? {
        return parameters!!.temperature()
    }

    fun topP(): Double? {
        return parameters!!.topP()
    }

    fun topK(): Int? {
        return parameters!!.topK()
    }

    fun frequencyPenalty(): Double? {
        return parameters!!.frequencyPenalty()
    }

    fun presencePenalty(): Double? {
        return parameters!!.presencePenalty()
    }

    fun maxOutputTokens(): Int? {
        return parameters!!.maxOutputTokens()
    }

    fun stopSequences(): List<String?>? {
        return parameters!!.stopSequences()
    }

    fun toolSpecifications(): List<ToolSpecification?>? {
        return parameters!!.toolSpecifications()
    }

    fun toolChoice(): ToolChoice? {
        return parameters!!.toolChoice()
    }

    fun responseFormat(): ResponseFormat? {
        return parameters!!.responseFormat()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ChatRequest
        return this.messages == that.messages
                && this.parameters == that.parameters
    }

    override fun hashCode(): Int {
        return Objects.hash(messages, parameters)
    }

    override fun toString(): String {
        return "ChatRequest {" +
                " messages = " + messages +
                ", parameters = " + parameters +
                " }"
    }

    class Builder {
        var messages: List<ChatMessage?>? = null
        var parameters: ChatRequestParameters? = null

        var modelName: String? = null
        var temperature: Double? = null
        var topP: Double? = null
        var topK: Int? = null
        var frequencyPenalty: Double? = null
        var presencePenalty: Double? = null
        var maxOutputTokens: Int? = null
        var stopSequences: List<String?>? = null
        var toolSpecifications: List<ToolSpecification?>? = null
        var toolChoice: ToolChoice? = null
        var responseFormat: ResponseFormat? = null

        fun messages(messages: List<ChatMessage?>?): Builder {
            this.messages = messages
            return this
        }

        fun messages(vararg messages: ChatMessage?): Builder {
            return messages(Arrays.asList(*messages))
        }

        fun parameters(parameters: ChatRequestParameters?): Builder {
            this.parameters = parameters
            return this
        }

        fun modelName(modelName: String?): Builder {
            this.modelName = modelName
            return this
        }

        fun temperature(temperature: Double?): Builder {
            this.temperature = temperature
            return this
        }

        fun topP(topP: Double?): Builder {
            this.topP = topP
            return this
        }

        fun topK(topK: Int?): Builder {
            this.topK = topK
            return this
        }

        fun frequencyPenalty(frequencyPenalty: Double?): Builder {
            this.frequencyPenalty = frequencyPenalty
            return this
        }

        fun presencePenalty(presencePenalty: Double?): Builder {
            this.presencePenalty = presencePenalty
            return this
        }

        fun maxOutputTokens(maxOutputTokens: Int?): Builder {
            this.maxOutputTokens = maxOutputTokens
            return this
        }

        fun stopSequences(stopSequences: List<String?>?): Builder {
            this.stopSequences = stopSequences
            return this
        }

        fun toolSpecifications(toolSpecifications: List<ToolSpecification?>?): Builder {
            this.toolSpecifications = toolSpecifications
            return this
        }

        fun toolSpecifications(vararg toolSpecifications: ToolSpecification?): Builder {
            return toolSpecifications(Arrays.asList(*toolSpecifications))
        }

        fun toolChoice(toolChoice: ToolChoice?): Builder {
            this.toolChoice = toolChoice
            return this
        }

        fun responseFormat(responseFormat: ResponseFormat?): Builder {
            this.responseFormat = responseFormat
            return this
        }

        fun build(): ChatRequest {
            return ChatRequest(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }

        private fun validate(builder: Builder, name: String) {
            require(builder.parameters == null) { "Cannot set both 'parameters' and '%s' on ChatRequest" }
        }
    }
}
