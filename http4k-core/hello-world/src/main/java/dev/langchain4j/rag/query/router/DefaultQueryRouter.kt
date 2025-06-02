package dev.langchain4j.rag.query.router

import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.query.Query
import java.util.Arrays
import java.util.Collections

/**
 * Default implementation of [QueryRouter] intended to be suitable for the majority of use cases.
 * <br></br>
 * <br></br>
 * It's important to note that while efforts will be made to avoid breaking changes,
 * the default behavior of this class may be updated in the future if it's found
 * that the current behavior does not adequately serve the majority of use cases.
 * Such changes would be made to benefit both current and future users.
 * <br></br>
 * <br></br>
 * This implementation always routes all [Query]s
 * to one or multiple [ContentRetriever]s provided in the constructor.
 *
 * @see LanguageModelQueryRouter
 */
class DefaultQueryRouter(contentRetrievers: Collection<ContentRetriever?>) : QueryRouter {
    private val contentRetrievers: Collection<ContentRetriever?> =
        Collections.unmodifiableCollection(
            ValidationUtils.ensureNotEmpty(
                contentRetrievers,
                "contentRetrievers"
            )
        )

    constructor(vararg contentRetrievers: ContentRetriever?) : this(Arrays.asList<ContentRetriever?>(*contentRetrievers))

    override fun route(query: Query): Collection<ContentRetriever?> {
        return contentRetrievers
    }
}
