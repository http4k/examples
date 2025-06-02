package dev.langchain4j.model.language

import dev.langchain4j.model.ModelDisabledException
import dev.langchain4j.model.input.Prompt
import dev.langchain4j.model.output.Response

/**
 * A [LanguageModel] which throws a [ModelDisabledException] for all of its methods
 *
 *
 * This could be used in tests, or in libraries that extend this one to conditionally enable or disable functionality.
 *
 */
class DisabledLanguageModel : LanguageModel {
    override fun generate(prompt: String?): Response<String> {
        throw ModelDisabledException("LanguageModel is disabled")
    }

    override fun generate(prompt: Prompt): Response<String> {
        throw ModelDisabledException("LanguageModel is disabled")
    }
}
