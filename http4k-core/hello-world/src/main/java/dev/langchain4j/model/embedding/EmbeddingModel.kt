package dev.langchain4j.model.embedding

import dev.langchain4j.data.embedding.Embedding
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.data.segment.TextSegment.Companion.from
import dev.langchain4j.model.output.Response

/**
 * Represents a model that can convert a given text into an embedding (vector representation of the text).
 */
interface EmbeddingModel {
    /**
     * Embed a text.
     *
     * @param text the text to embed.
     * @return the embedding.
     */
    fun embed(text: String?): Response<Embedding> {
        return embed(from(text))
    }

    /**
     * Embed the text content of a TextSegment.
     *
     * @param textSegment the text segment to embed.
     * @return the embedding.
     */
    fun embed(textSegment: TextSegment): Response<Embedding> {
        val response = embedAll(listOf(textSegment))
        response.content().size
        arrayOf<Any?>(
            response.content().size
        )

        return Response.from(response.content()[0], response.tokenUsage(), response.finishReason())
    }

    /**
     * Embeds the text content of a list of TextSegments.
     *
     * @param textSegments the text segments to embed.
     * @return the embeddings.
     */
    fun embedAll(textSegments: List<TextSegment?>?): Response<List<Embedding>>

    /**
     * Returns the dimension of the [Embedding] produced by this embedding model.
     *
     * @return dimension of the embedding
     */
    fun dimension(): Int {
        return embed("test").content().dimension()
    }
}
