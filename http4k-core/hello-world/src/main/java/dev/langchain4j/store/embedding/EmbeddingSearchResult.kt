package dev.langchain4j.store.embedding

/**
 * Represents a result of a search in an [EmbeddingStore].
 */
class EmbeddingSearchResult<Embedded>(matches: List<EmbeddingMatch<Embedded>>) {
    private val matches: List<EmbeddingMatch<Embedded>> =
        matches!!

    fun matches(): List<EmbeddingMatch<Embedded>> {
        return matches
    }
}
