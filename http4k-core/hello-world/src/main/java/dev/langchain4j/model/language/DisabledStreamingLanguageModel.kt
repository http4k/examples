package dev.langchain4j.model.language

import dev.langchain4j.model.ModelDisabledException
import dev.langchain4j.model.StreamingResponseHandler
import dev.langchain4j.model.input.Prompt

/**
 * A [StreamingLanguageModel] which throws a [ModelDisabledException] for all of its methods
 *
 *
 * This could be used in tests, or in libraries that extend this one to conditionally enable or disable functionality.
 *
 */
class DisabledStreamingLanguageModel : StreamingLanguageModel {
    override fun generate(prompt: String?, handler: StreamingResponseHandler<String?>?) {
        throw ModelDisabledException("StreamingLanguageModel is disabled")
    }

    override fun generate(prompt: Prompt, handler: StreamingResponseHandler<String?>?) {
        throw ModelDisabledException("StreamingLanguageModel is disabled")
    }
}
