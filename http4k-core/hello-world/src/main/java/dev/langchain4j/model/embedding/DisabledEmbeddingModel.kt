package dev.langchain4j.model.embedding

import dev.langchain4j.data.embedding.Embedding
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.ModelDisabledException
import dev.langchain4j.model.output.Response

/**
 * An [EmbeddingModel] which throws a [ModelDisabledException] for all of its methods
 *
 *
 * This could be used in tests, or in libraries that extend this one to conditionally enable or disable functionality.
 *
 */
class DisabledEmbeddingModel : EmbeddingModel {
    override fun embed(text: String?): Response<Embedding> {
        throw ModelDisabledException("EmbeddingModel is disabled")
    }

    override fun embed(textSegment: TextSegment): Response<Embedding> {
        throw ModelDisabledException("EmbeddingModel is disabled")
    }

    override fun embedAll(textSegments: List<TextSegment?>?): Response<List<Embedding>> {
        throw ModelDisabledException("EmbeddingModel is disabled")
    }
}
