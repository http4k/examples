package dev.langchain4j.model.scoring

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.data.segment.TextSegment.Companion.from
import dev.langchain4j.model.output.Response

/**
 * Represents a model capable of scoring a text against a query.
 * <br></br>
 * Useful for identifying the most relevant texts when scoring multiple texts against the same query.
 * <br></br>
 * The scoring model can be employed for re-ranking purposes.
 */
interface ScoringModel {
    /**
     * Scores a given text against a given query.
     *
     * @param text  The text to be scored.
     * @param query The query against which to score the text.
     * @return the score.
     */
    fun score(text: String?, query: String?): Response<Double> {
        return score(from(text), query)
    }

    /**
     * Scores a given [TextSegment] against a given query.
     *
     * @param segment The [TextSegment] to be scored.
     * @param query   The query against which to score the segment.
     * @return the score.
     */
    fun score(segment: TextSegment, query: String?): Response<Double> {
        val response = scoreAll(listOf(segment), query)
        response.content().size
        arrayOf<Any?>(
            response.content().size
        )
        return Response.from(response.content()[0], response.tokenUsage(), response.finishReason())
    }

    /**
     * Scores all provided [TextSegment]s against a given query.
     *
     * @param segments The list of [TextSegment]s to score.
     * @param query    The query against which to score the segments.
     * @return the list of scores. The order of scores corresponds to the order of [TextSegment]s.
     */
    fun scoreAll(segments: List<TextSegment?>?, query: String?): Response<List<Double>>
}
