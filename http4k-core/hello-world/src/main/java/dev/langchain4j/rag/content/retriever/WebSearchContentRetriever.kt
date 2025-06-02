package dev.langchain4j.rag.content.retriever

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.rag.content.Content
import dev.langchain4j.rag.query.Query
import dev.langchain4j.web.search.WebSearchEngine
import dev.langchain4j.web.search.WebSearchRequest
import java.util.stream.Collectors

/**
 * A [ContentRetriever] that retrieves relevant [Content] from the web using a [WebSearchEngine].
 * <br></br>
 * It returns one [Content] for each result that a [WebSearchEngine] has returned for a given [Query].
 * <br></br>
 * Depending on the [WebSearchEngine] implementation, the [Content.textSegment]
 * can contain either a snippet of a web page or a complete content of a web page.
 */
class WebSearchContentRetriever(webSearchEngine: WebSearchEngine?, maxResults: Int?) : ContentRetriever {
    private val webSearchEngine: WebSearchEngine
    private val maxResults: Int

    init {
        this.webSearchEngine = webSearchEngine!!
        this.maxResults = maxResults!!
    }

    override fun retrieve(query: Query): List<Content> {
        val webSearchRequest = WebSearchRequest.builder()
            .searchTerms(query.text())
            .maxResults(maxResults)
            .build()

        val webSearchResults = webSearchEngine.search(webSearchRequest)

        return webSearchResults.toTextSegments().stream()
            .map<Content> { textSegment: TextSegment -> Content.Companion.from(textSegment) }
            .collect(Collectors.toList<Content>())
    }

    class WebSearchContentRetrieverBuilder internal constructor() {
        private var webSearchEngine: WebSearchEngine? = null
        private var maxResults: Int? = null

        fun webSearchEngine(webSearchEngine: WebSearchEngine?): WebSearchContentRetrieverBuilder {
            this.webSearchEngine = webSearchEngine
            return this
        }

        fun maxResults(maxResults: Int?): WebSearchContentRetrieverBuilder {
            this.maxResults = maxResults
            return this
        }

        fun build(): WebSearchContentRetriever {
            return WebSearchContentRetriever(this.webSearchEngine, this.maxResults)
        }
    }

    companion object {
        fun builder(): WebSearchContentRetrieverBuilder {
            return WebSearchContentRetrieverBuilder()
        }
    }
}
