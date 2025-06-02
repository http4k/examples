package dev.langchain4j.rag.content.aggregator

import dev.langchain4j.rag.content.Content
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.query.Query

/**
 * Aggregates all [Content]s retrieved from all [ContentRetriever]s using all [Query]s.
 * <br></br>
 * The goal is to ensure that only the most relevant and non-redundant [Content]s are presented to the LLM.
 * <br></br>
 * Some effective approaches include:
 * <pre>
 * - Re-ranking (see [ReRankingContentAggregator])
 * - Reciprocal Rank Fusion (see [ReciprocalRankFuser], utilized in both [DefaultContentAggregator] and [ReRankingContentAggregator])
</pre> *
 *
 * @see DefaultContentAggregator
 *
 * @see ReRankingContentAggregator
 */
interface ContentAggregator {
    /**
     * Aggregates all [Content]s retrieved by all [ContentRetriever]s using all [Query]s.
     * The [Content]s, both on input and output, are sorted by relevance,
     * with the most relevant [Content]s appearing at the beginning of `List<Content>`.
     *
     * @param queryToContents A map from a [Query] to all `List<Content>` retrieved with that [Query].
     * Given that each [Query] can be routed to multiple [ContentRetriever]s, the
     * value of this map is a `Collection<List<Content>>`
     * rather than a simple `List<Content>`.
     * @return A list of aggregated [Content]s.
     */
    fun aggregate(queryToContents: Map<Query?, Collection<List<Content?>?>>): List<Content?>
}
