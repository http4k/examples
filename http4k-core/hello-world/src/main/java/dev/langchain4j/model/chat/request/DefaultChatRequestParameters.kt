package dev.langchain4j.model.chat.request

import dev.langchain4j.agent.tool.ToolSpecification
import dev.langchain4j.internal.Utils
import dev.langchain4j.model.chat.request.json.JsonSchema
import java.util.Arrays
import java.util.Objects

class DefaultChatRequestParameters protected constructor(builder: Builder<*>) :
    ChatRequestParameters {
    private val modelName: String?
    private val temperature: Double?
    private val topP: Double?
    private val topK: Int?
    private val frequencyPenalty: Double?
    private val presencePenalty: Double?
    private val maxOutputTokens: Int?
    private val stopSequences: List<String?>
    private val toolSpecifications: List<ToolSpecification?>
    private val toolChoice: ToolChoice?
    private val responseFormat: ResponseFormat?

    init {
        this.modelName = builder.modelName
        this.temperature = builder.temperature
        this.topP = builder.topP
        this.topK = builder.topK
        this.frequencyPenalty = builder.frequencyPenalty
        this.presencePenalty = builder.presencePenalty
        this.maxOutputTokens = builder.maxOutputTokens
        this.stopSequences = Utils.copy(builder.stopSequences)
        this.toolSpecifications = Utils.copy(builder.toolSpecifications)
        this.toolChoice = builder.toolChoice
        this.responseFormat = builder.responseFormat
    }

    override fun modelName(): String? {
        return modelName
    }

    override fun temperature(): Double? {
        return temperature
    }

    override fun topP(): Double? {
        return topP
    }

    override fun topK(): Int? {
        return topK
    }

    override fun frequencyPenalty(): Double? {
        return frequencyPenalty
    }

    override fun presencePenalty(): Double? {
        return presencePenalty
    }

    override fun maxOutputTokens(): Int? {
        return maxOutputTokens
    }

    override fun stopSequences(): List<String?> {
        return stopSequences
    }

    override fun toolSpecifications(): List<ToolSpecification?> {
        return toolSpecifications
    }

    override fun toolChoice(): ToolChoice? {
        return toolChoice
    }

    override fun responseFormat(): ResponseFormat? {
        return responseFormat
    }

    override fun overrideWith(that: ChatRequestParameters): ChatRequestParameters {
        return builder()
            .overrideWith(this)!!
            .overrideWith(that)!!
            .build()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as DefaultChatRequestParameters
        return modelName == that.modelName
                && Objects.equals(temperature, that.temperature)
                && Objects.equals(topP, that.topP)
                && topK == that.topK
                && Objects.equals(frequencyPenalty, that.frequencyPenalty)
                && Objects.equals(presencePenalty, that.presencePenalty)
                && maxOutputTokens == that.maxOutputTokens
                && stopSequences == that.stopSequences
                && toolSpecifications == that.toolSpecifications
                && toolChoice == that.toolChoice
                && responseFormat == that.responseFormat
    }

    override fun hashCode(): Int {
        return Objects.hash(
            modelName,
            temperature,
            topP,
            topK,
            frequencyPenalty,
            presencePenalty,
            maxOutputTokens,
            stopSequences,
            toolSpecifications,
            toolChoice,
            responseFormat
        )
    }

    override fun toString(): String {
        return "DefaultChatRequestParameters{" +
                "modelName='" + modelName + '\'' +
                ", temperature=" + temperature +
                ", topP=" + topP +
                ", topK=" + topK +
                ", frequencyPenalty=" + frequencyPenalty +
                ", presencePenalty=" + presencePenalty +
                ", maxOutputTokens=" + maxOutputTokens +
                ", stopSequences=" + stopSequences +
                ", toolSpecifications=" + toolSpecifications +
                ", toolChoice=" + toolChoice +
                ", responseFormat=" + responseFormat +
                '}'
    }

    class Builder<T : Builder<T>?> {
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

        fun overrideWith(parameters: ChatRequestParameters): T {
            modelName(Utils.getOrDefault(parameters.modelName(), modelName))
            temperature(Utils.getOrDefault(parameters.temperature(), temperature))
            topP(Utils.getOrDefault(parameters.topP(), topP))
            topK(Utils.getOrDefault(parameters.topK(), topK))
            frequencyPenalty(Utils.getOrDefault(parameters.frequencyPenalty(), frequencyPenalty))
            presencePenalty(Utils.getOrDefault(parameters.presencePenalty(), presencePenalty))
            maxOutputTokens(Utils.getOrDefault(parameters.maxOutputTokens(), maxOutputTokens))
            stopSequences(Utils.getOrDefault(parameters.stopSequences(), stopSequences))
            toolSpecifications(Utils.getOrDefault(parameters.toolSpecifications(), toolSpecifications))
            toolChoice(Utils.getOrDefault(parameters.toolChoice(), toolChoice))
            responseFormat(Utils.getOrDefault(parameters.responseFormat(), responseFormat))
            return this as T
        }

        fun modelName(modelName: String?): T {
            this.modelName = modelName
            return this as T
        }

        fun temperature(temperature: Double?): T {
            this.temperature = temperature
            return this as T
        }

        fun topP(topP: Double?): T {
            this.topP = topP
            return this as T
        }

        fun topK(topK: Int?): T {
            this.topK = topK
            return this as T
        }

        fun frequencyPenalty(frequencyPenalty: Double?): T {
            this.frequencyPenalty = frequencyPenalty
            return this as T
        }

        fun presencePenalty(presencePenalty: Double?): T {
            this.presencePenalty = presencePenalty
            return this as T
        }

        fun maxOutputTokens(maxOutputTokens: Int?): T {
            this.maxOutputTokens = maxOutputTokens
            return this as T
        }

        /**
         * @see .stopSequences
         */
        fun stopSequences(stopSequences: List<String?>?): T {
            this.stopSequences = stopSequences
            return this as T
        }

        /**
         * @see .stopSequences
         */
        fun stopSequences(vararg stopSequences: String?): T {
            return stopSequences(Arrays.asList(*stopSequences))
        }

        /**
         * @see .toolSpecifications
         */
        fun toolSpecifications(toolSpecifications: List<ToolSpecification?>?): T {
            this.toolSpecifications = toolSpecifications
            return this as T
        }

        /**
         * @see .toolSpecifications
         */
        fun toolSpecifications(vararg toolSpecifications: ToolSpecification?): T {
            return toolSpecifications(Arrays.asList(*toolSpecifications))
        }

        fun toolChoice(toolChoice: ToolChoice?): T {
            this.toolChoice = toolChoice
            return this as T
        }

        /**
         * @see .responseFormat
         */
        fun responseFormat(responseFormat: ResponseFormat?): T {
            this.responseFormat = responseFormat
            return this as T
        }

        /**
         * @see .responseFormat
         */
        fun responseFormat(jsonSchema: JsonSchema?): T {
            if (jsonSchema != null) {
                val responseFormat: ResponseFormat = ResponseFormat.Companion.builder()
                    .type(ResponseFormatType.JSON)
                    .jsonSchema(jsonSchema)
                    .build()
                return responseFormat(responseFormat)
            }
            return this as T
        }

        fun build(): ChatRequestParameters {
            return DefaultChatRequestParameters(this)
        }
    }

    companion object {
        fun builder(): Builder<*> {
            TODO()
//            return Builder<T>()
        }
    }
}
