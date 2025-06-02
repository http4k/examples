package dev.langchain4j.agent.tool

/**
 * Java methods annotated with `@Tool` are considered tools/functions that language model can execute/call.
 * Tool/function calling LLM capability (e.g., see [OpenAI function calling documentation](https://platform.openai.com/docs/guides/function-calling))
 * is used under the hood.
 * When used together with `AiServices`, a low-level [ToolSpecification] will be automatically created
 * from the method signature (e.g. method name, method parameters (names and types), `@Tool`
 * and @[P] annotations, etc.) and will be sent to the LLM.
 * If the LLM decides to call the tool, the arguments will be parsed, and the method will be called automatically.
 * If the return type of the method annotated with `@Tool` is [String], the returned value will be sent to the LLM as-is.
 * If the return type is `void`, "Success" string will be sent to the LLM.
 * In all other cases, the returned value will be serialized into a JSON string and sent to the LLM.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class Tool(
    /**
     * Name of the tool. If not provided, method name will be used.
     *
     * @return name of the tool.
     */
    val name: String = "",
    /**
     * Description of the tool.
     * It should be clear and descriptive to allow language model to understand the tool's purpose and its intended use.
     *
     * @return description of the tool.
     */
    vararg val value: String = [""]
)
