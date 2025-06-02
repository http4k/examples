package dev.langchain4j.rag.content.aggregator

import dev.langchain4j.rag.content.Content
import dev.langchain4j.rag.query.Query

/**
 * Default implementation of [ContentAggregator] intended to be suitable for the majority of use cases.
 * <br></br>
 * <br></br>
 * It's important to note that while efforts will be made to avoid breaking changes,
 * the default behavior of this class may be updated in the future if it's found
 * that the current behavior does not adequately serve the majority of use cases.
 * Such changes would be made to benefit both current and future users.
 * <br></br>
 * <br></br>
 * This implementation employs Reciprocal Rank Fusion (see [ReciprocalRankFuser]) in two stages
 * to aggregate all `Collection<List<Content>>` into a single `List<Content>`.
 * The [Content]s in both the input and output lists are expected to be sorted by relevance,
 * with the most relevant [Content]s at the beginning of the `List<Content>`.
 * <br></br>
 * Stage 1: For each [Query], all `List<Content>` retrieved with that [Query]
 * are merged into a single `List<Content>`.
 * <br></br>
 * Stage 2: All `List<Content>` (results from stage 1) are merged into a single `List<Content>`.
 * <br></br>
 * <br></br>
 * **Example:**
 * <br></br>
 * Input (query -&gt; multiple lists with ranked contents):
 * <pre>
 * home animals -&gt; [cat, dog, hamster], [cat, parrot]
 * domestic animals -&gt; [dog, horse], [cat]
</pre> *
 * After stage 1 (query -&gt; single list with ranked contents):
 * <br></br>
 * <pre>
 * home animals -&gt; [cat, dog, parrot, hamster]
 * domestic animals -&gt; [dog, cat, horse]
</pre> *
 * After stage 2 (single list with ranked contents):
 * <br></br>
 * <pre>
 * [cat, dog, parrot, horse, hamster]
</pre> *
 *
 * @see ReciprocalRankFuser
 *
 * @see ReRankingContentAggregator
 */
class DefaultContentAggregator : ContentAggregator {
    override fun aggregate(queryToContents: Map<Query?, Collection<List<Content?>?>>): List<Content?> {
        // First, for each query, fuse all contents retrieved from different sources using that query.

        val fused = fuse(queryToContents)

        return TODO()
        // Then, fuse all contents retrieved using all queries
//        return fuse(fused.values)
    }

    protected fun fuse(queryToContents: Map<Query?, Collection<List<Content?>?>>): Map<Query?, List<Content?>?> {
        val fused: MutableMap<Query?, List<Content?>?> = LinkedHashMap()
        for (query in queryToContents.keys) {
            val contents =
                queryToContents[query]!!
//            fused[query] = fuse(contents)
        }
        return fused
    }
}
