package dev.langchain4j.rag.query.transformer

import dev.langchain4j.rag.query.Query

/**
 * Default implementation of [QueryTransformer] intended to be suitable for the majority of use cases.
 * <br></br>
 * <br></br>
 * It's important to note that while efforts will be made to avoid breaking changes,
 * the default behavior of this class may be updated in the future if it's found
 * that the current behavior does not adequately serve the majority of use cases.
 * Such changes would be made to benefit both current and future users.
 * <br></br>
 * <br></br>
 * This implementation simply returns the provided [Query] without any transformation.
 *
 * @see CompressingQueryTransformer
 *
 * @see ExpandingQueryTransformer
 */
class DefaultQueryTransformer : QueryTransformer {
    override fun transform(query: Query): Collection<Query> {
        return listOf(query)
    }
}
