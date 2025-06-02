package dev.langchain4j.store.embedding

import dev.langchain4j.internal.ValidationUtils

/**
 * Represents a result of a search in an [EmbeddingStore].
 */
class EmbeddingSearchResult<Embedded>(matches: List<EmbeddingMatch<Embedded>>) {
    private val matches: List<EmbeddingMatch<Embedded>> =
        ValidationUtils.ensureNotNull(
            matches,
            "matches"
        )

    fun matches(): List<EmbeddingMatch<Embedded>> {
        return matches
    }
}
