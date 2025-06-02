package dev.langchain4j.rag.content.aggregator

import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.rag.content.Content

/**
 * Implementation of Reciprocal Rank Fusion.
 * <br></br>
 * A comprehensive explanation can be found
 * [here](https://learn.microsoft.com/en-us/azure/search/hybrid-search-ranking).
 */
object ReciprocalRankFuser {
    /**
     * Fuses multiple `List<Content>` into a single `List<Content>`
     * using the Reciprocal Rank Fusion (RRF) algorithm.
     *
     * @param listsOfContents A [Collection] of `List<Content>` to be fused together.
     * @param k               A ranking constant used to control the influence of individual ranks
     * from different ranked lists being combined. A common value used is 60,
     * based on empirical studies. However, the optimal value may vary depending
     * on the specific application and the characteristics of the data.
     * A larger value diminishes the differences between the ranks,
     * leading to a more uniform distribution of fusion scores.
     * A smaller value amplifies the importance of the top-ranked items in each list.
     * K must be greater than or equal to 1.
     * @return A single `List<Content>`, the result of the fusion.
     */
    /**
     * Fuses multiple `List<Content>` into a single `List<Content>`
     * using the Reciprocal Rank Fusion (RRF) algorithm with k=60.
     *
     * @param listsOfContents A [Collection] of `List<Content>` to be fused together.
     * @return A single `List<Content>`, the result of the fusion.
     */
    @JvmOverloads
    fun fuse(listsOfContents: Collection<List<Content>>, k: Int = 60): List<Content> {
        ValidationUtils.ensureBetween(k, 1, Int.MAX_VALUE, "k")

        val scores: MutableMap<Content, Double> = LinkedHashMap()
        for (singleListOfContent in listsOfContents) {
            for (i in singleListOfContent.indices) {
                val content = singleListOfContent[i]
                val currentScore = scores.getOrDefault(content, 0.0)
                val rank = i + 1
                val newScore = currentScore + 1.0 / (k + rank)
                scores[content] = newScore
            }
        }

        val fused: List<Content> = ArrayList(scores.keys)
//        fused.sort(Comparator.comparingDouble { key: Any? -> scores[key]!! }.reversed())
        return fused
    }
}
