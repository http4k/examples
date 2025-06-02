package dev.langchain4j.web.search

/**
 * Represents a web search engine that can be used to perform searches on the Web in response to a user query.
 */
interface WebSearchEngine {
    /**
     * Performs a search query on the web search engine and returns the search results.
     *
     * @param query the search query
     * @return the search results
     */
    fun search(query: String?): WebSearchResults {
        return search(WebSearchRequest.Companion.from(query))
    }

    /**
     * Performs a search request on the web search engine and returns the search results.
     *
     * @param webSearchRequest the search request
     * @return the web search results
     */
    fun search(webSearchRequest: WebSearchRequest?): WebSearchResults
}
