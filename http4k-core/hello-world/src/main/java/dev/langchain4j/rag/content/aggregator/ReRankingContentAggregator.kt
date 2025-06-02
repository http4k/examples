package dev.langchain4j.rag.content.aggregator

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.internal.Utils
import dev.langchain4j.model.scoring.ScoringModel
import dev.langchain4j.rag.content.Content
import dev.langchain4j.rag.content.ContentMetadata
import dev.langchain4j.rag.query.Query
import dev.langchain4j.rag.query.transformer.ExpandingQueryTransformer
import java.util.function.Function
import java.util.stream.Collectors

/**
 * A [ContentAggregator] that performs re-ranking using a [ScoringModel], such as Cohere.
 * <br></br>
 * The [ScoringModel] scores [Content]s against a (single) [Query].
 * If multiple [Query]s are input to this aggregator
 * (for example, when using [ExpandingQueryTransformer]),
 * a [.querySelector] must be provided to select a [Query] for ranking all [Content]s.
 * Alternatively, a custom implementation can be created to score [Content]s against the [Query]s
 * that were used for their retrieval (instead of a single [Query]), and then re-rank based on those scores.
 * Although potentially more costly, this method may yield better results
 * when the [Query]s are significantly different.
 * <br></br>
 * <br></br>
 * Before the use of a [ScoringModel], all [Content]s are fused in the same way
 * as by the [DefaultContentAggregator]. For detailed information, please refer to its Javadoc.
 * <br></br>
 * <br></br>
 * Configurable parameters (optional):
 * <br></br>
 * - [.minScore]: the minimum score for [Content]s to be returned.
 * [Content]s scoring below this threshold (as determined by the [ScoringModel])
 * are excluded from the results.
 *
 * @see DefaultContentAggregator
 */
class ReRankingContentAggregator @JvmOverloads constructor(
    scoringModel: ScoringModel?,
    querySelector: Function<Map<Query, Collection<List<Content?>?>>, Query>? = DEFAULT_QUERY_SELECTOR,
    private val minScore: Double? = null,
    maxResults: Int? = null
) :
    ContentAggregator {
    private val scoringModel: ScoringModel
    private val querySelector: Function<Map<Query, Collection<List<Content?>?>>, Query>?
    private val maxResults: Int

    init {
        this.scoringModel = scoringModel!!
        this.querySelector = Utils.getOrDefault(querySelector, DEFAULT_QUERY_SELECTOR)
        this.maxResults = Utils.getOrDefault(maxResults, Int.MAX_VALUE)
    }

    override fun aggregate(queryToContents: Map<Query?, Collection<List<Content?>?>>): List<Content?> {
        if (queryToContents.isEmpty()) {
            return emptyList<Content>()
        }

        return TODO()

//        // Select a query against which all contents will be re-ranked
//        val query = querySelector!!.apply(queryToContents)
//
//        // For each query, fuse all contents retrieved from different sources using that query
//        val queryToFusedContents = fuse(queryToContents)
//
//        // Fuse all contents retrieved using all queries
//        val fusedContents: List<Content?> = fuse(queryToFusedContents.values)
//
//        if (fusedContents.isEmpty()) {
//            return fusedContents
//        }
//
//        // Re-rank all the fused contents against the query selected by the query selector
//        return reRankAndFilter(fusedContents, query)
    }

    protected fun fuse(queryToContents: Map<Query?, Collection<List<Content?>?>>): Map<Query?, List<Content?>?> {
        val fused: MutableMap<Query?, List<Content?>?> = LinkedHashMap()
//        for (query in queryToContents.keys) {
//            val contents =
//                queryToContents[query]!!
//            fused[query] = fuse(contents)
//        }
        return fused
    }

    protected fun reRankAndFilter(contents: List<Content?>, query: Query): List<Content?> {
        val segments = contents.stream()
            .map { obj: Content? -> obj!!.textSegment() }
            .collect(Collectors.toList())

        val scores = scoringModel.scoreAll(segments, query.text()).content()

        val segmentToScore: MutableMap<TextSegment?, Double> = HashMap()
        for (i in segments.indices) {
            segmentToScore[segments[i]] = scores[i]
        }

        return segmentToScore.entries.stream()
            .filter { entry: Map.Entry<TextSegment?, Double> -> minScore == null || entry.value >= minScore }
            .sorted(java.util.Map.Entry.comparingByValue<TextSegment?, Double>().reversed())
            .map<Content?> { entry: Map.Entry<TextSegment?, Double> ->
                Content.Companion.from(
                    entry.key!!, java.util.Map.of<ContentMetadata?, Any>(ContentMetadata.RERANKED_SCORE, entry.value)
                )
            }
            .limit(maxResults.toLong())
            .collect(Collectors.toList<Content?>())
    }

    class ReRankingContentAggregatorBuilder internal constructor() {
        private var scoringModel: ScoringModel? = null
        private var querySelector: Function<Map<Query, Collection<List<Content?>?>>, Query>? = null
        private var minScore: Double? = null
        private var maxResults: Int? = null

        fun scoringModel(scoringModel: ScoringModel?): ReRankingContentAggregatorBuilder {
            this.scoringModel = scoringModel
            return this
        }

        fun querySelector(querySelector: Function<Map<Query, Collection<List<Content?>?>>, Query>?): ReRankingContentAggregatorBuilder {
            this.querySelector = querySelector
            return this
        }

        fun minScore(minScore: Double?): ReRankingContentAggregatorBuilder {
            this.minScore = minScore
            return this
        }

        fun maxResults(maxResults: Int?): ReRankingContentAggregatorBuilder {
            this.maxResults = maxResults
            return this
        }

        fun build(): ReRankingContentAggregator {
            return ReRankingContentAggregator(this.scoringModel, this.querySelector, this.minScore, this.maxResults)
        }
    }

    companion object {
        val DEFAULT_QUERY_SELECTOR: Function<Map<Query, Collection<List<Content?>?>>, Query> =
            Function { queryToContents: Map<Query, Collection<List<Content?>?>> ->
                if (queryToContents.size > 1) {
                    throw IllegalArgumentException(
                        String.format(
                            "The 'queryToContents' contains %s queries, making the re-ranking ambiguous. " +
                                "Because there are multiple queries, it is unclear which one should be " +
                                "used for re-ranking. Please provide a 'querySelector' in the constructor/builder.",
                            queryToContents.size
                        )
                    )
                }
                queryToContents.keys.iterator().next()
            }

        fun builder(): ReRankingContentAggregatorBuilder {
            return ReRankingContentAggregatorBuilder()
        }
    }
}
