package dev.langchain4j.spi.model.embedding

import dev.langchain4j.model.embedding.EmbeddingModel

/**
 * A factory for creating [EmbeddingModel] instances through SPI.
 * <br></br>
 * For the "Easy RAG", import `langchain4j-easy-rag` module,
 * which contains a `EmbeddingModelFactory` implementation.
 */
interface EmbeddingModelFactory {
    fun create(): EmbeddingModel?
}
