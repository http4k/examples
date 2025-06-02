package dev.langchain4j.web.search

import dev.langchain4j.data.document.Document
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.internal.Utils
import java.util.Objects
import java.util.stream.Collectors

/**
 * Represents the response of a web search performed.
 * This includes the list of organic search results, information about the search, and pagination information.
 *
 *
 * [WebSearchResults] follow opensearch foundation standard implemented by most web search engine libs like Google, Bing, Yahoo, etc.
 * [OpenSearch#response](https://github.com/dewitt/opensearch/blob/master/opensearch-1-1-draft-6.md#examples-of-opensearch-responses)
 *
 *
 *
 * The organic search results are the web pages that are returned by the search engine in response to a search query.
 * These results are typically ranked by relevance to the search query.
 */
class WebSearchResults(
    searchMetadata: Map<String?, Any?>?,
    searchInformation: WebSearchInformationResult,
    results: List<WebSearchOrganicResult?>?
) {
    private val searchMetadata: Map<String?, Any?> =
        Utils.copy(searchMetadata)
    private val searchInformation: WebSearchInformationResult =
        searchInformation!!
    private val results: List<WebSearchOrganicResult?> = Utils.copy(results)

    /**
     * Constructs a new instance of WebSearchResults.
     *
     * @param searchInformation The information about the web search.
     * @param results           The list of organic search results.
     */
    constructor(
        searchInformation: WebSearchInformationResult,
        results: List<WebSearchOrganicResult?>?
    ) : this(java.util.Map.of<String?, Any?>(), searchInformation, results)

    /**
     * Gets the metadata associated with the web search.
     *
     * @return The metadata associated with the web search.
     */
    fun searchMetadata(): Map<String?, Any?> {
        return searchMetadata
    }

    /**
     * Gets the information about the web search.
     *
     * @return The information about the web search.
     */
    fun searchInformation(): WebSearchInformationResult {
        return searchInformation
    }

    /**
     * Gets the list of organic search results.
     *
     * @return The list of organic search results.
     */
    fun results(): List<WebSearchOrganicResult?> {
        return results
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as WebSearchResults
        return searchMetadata == that.searchMetadata
                && searchInformation == that.searchInformation
                && results == that.results
    }

    override fun hashCode(): Int {
        return Objects.hash(searchMetadata, searchInformation, results)
    }

    override fun toString(): String {
        return "WebSearchResults{" +
                "searchMetadata=" + searchMetadata +
                ", searchInformation=" + searchInformation +
                ", results=" + results +
                '}'
    }

    /**
     * Converts the organic search results to a list of text segments.
     *
     * @return The list of text segments.
     */
    fun toTextSegments(): List<TextSegment> {
        return results.stream()
            .map { obj: WebSearchOrganicResult? -> obj!!.toTextSegment() }
            .collect(Collectors.toList())
    }

    /**
     * Converts the organic search results to a list of documents.
     *
     * @return The list of documents.
     */
    fun toDocuments(): List<Document> {
        return results.stream()
            .map { obj: WebSearchOrganicResult? -> obj!!.toDocument() }
            .collect(Collectors.toList())
    }

    companion object {
        /**
         * Creates a new instance of WebSearchResults from the specified parameters.
         *
         * @param results           The list of organic search results.
         * @param searchInformation The information about the web search.
         * @return The new instance of WebSearchResults.
         */
        fun from(
            searchInformation: WebSearchInformationResult,
            results: List<WebSearchOrganicResult?>?
        ): WebSearchResults {
            return WebSearchResults(searchInformation, results)
        }

        /**
         * Creates a new instance of WebSearchResults from the specified parameters.
         *
         * @param searchMetadata    The metadata associated with the search results.
         * @param searchInformation The information about the web search.
         * @param results           The list of organic search results.
         * @return The new instance of WebSearchResults.
         */
        fun from(
            searchMetadata: Map<String?, Any?>?,
            searchInformation: WebSearchInformationResult,
            results: List<WebSearchOrganicResult?>?
        ): WebSearchResults {
            return WebSearchResults(searchMetadata, searchInformation, results)
        }
    }
}
