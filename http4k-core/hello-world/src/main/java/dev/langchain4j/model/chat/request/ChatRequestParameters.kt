package dev.langchain4j.model.chat.request

import dev.langchain4j.agent.tool.ToolSpecification

/**
 * Represents common chat request parameters supported by most LLM providers.
 * Specific LLM provider integrations can extend this interface to add provider-specific parameters.
 *
 * @see DefaultChatRequestParameters
 */
interface ChatRequestParameters {
    fun modelName(): String?

    fun temperature(): Double?

    fun topP(): Double?

    fun topK(): Int?

    fun frequencyPenalty(): Double?

    fun presencePenalty(): Double?

    fun maxOutputTokens(): Int?

    fun stopSequences(): List<String?>

    fun toolSpecifications(): List<ToolSpecification?>

    fun toolChoice(): ToolChoice?

    fun responseFormat(): ResponseFormat?

    /**
     * Creates a new [ChatRequestParameters] by combining the current parameters with the specified ones.
     * Values from the specified parameters override values from the current parameters when there is overlap.
     * Neither the current nor the specified [ChatRequestParameters] objects are modified.
     *
     *
     * Example:
     * <pre>
     * Current parameters:
     * temperature = 1.0
     * maxOutputTokens = 100
     *
     * Specified parameters:
     * temperature = 0.5
     * modelName = my-model
     *
     * Result:
     * temperature = 0.5        // Overridden from specified
     * maxOutputTokens = 100    // Preserved from current
     * modelName = my-model     // Added from specified
    </pre> *
     *
     * @param parameters the parameters whose values will override the current ones
     * @return a new [ChatRequestParameters] instance combining both sets of parameters
     */
    fun overrideWith(parameters: ChatRequestParameters): ChatRequestParameters

    companion object {
        fun builder(): DefaultChatRequestParameters.Builder<*> {
            return TODO()
//            return DefaultChatRequestParameters.Builder<*>()
        }
    }
}
