package dev.langchain4j.store.embedding

import dev.langchain4j.model.output.TokenUsage

/**
 * Represents the result of a [EmbeddingStoreIngestor] ingestion process.
 */
class IngestionResult(
    /**
     * The token usage information.
     */
    private val tokenUsage: TokenUsage
) {
    fun tokenUsage(): TokenUsage {
        return tokenUsage
    }
}
