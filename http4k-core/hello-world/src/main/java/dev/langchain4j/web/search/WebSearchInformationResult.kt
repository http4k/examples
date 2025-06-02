package dev.langchain4j.web.search

import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Objects

/**
 * Represents general information about the web search performed.
 * This includes the total number of results, the page number, and metadata.
 *
 *
 * The total number of results is the total number of web pages that are found by the search engine in response to a search query.
 * The page number is the current page number of the search results.
 * The metadata is a map of key-value pairs that provide additional information about the search.
 * For example, it could include the search query, the search engine used, the time it took to perform the search, etc.
 */
class WebSearchInformationResult @JvmOverloads constructor(
    totalResults: Long,
    private val pageNumber: Int? = null,
    metadata: Map<String, Any>? = null
) {
    private val totalResults: Long =
        ValidationUtils.ensureNotNull(totalResults, "totalResults")
    private val metadata: Map<String, Any> = Utils.copy(metadata)

    /**
     * Constructs a new WebSearchInformationResult with the specified total results, page number, and metadata.
     *
     * @param totalResults The total number of results.
     * @param pageNumber  The page number.
     * @param metadata     The metadata.
     */

    /**
     * Gets the total number of results.
     *
     * @return The total number of results.
     */
    fun totalResults(): Long {
        return totalResults
    }

    /**
     * Gets the page number.
     *
     * @return The page number.
     */
    fun pageNumber(): Int? {
        return pageNumber
    }

    /**
     * Gets the metadata.
     *
     * @return The metadata.
     */
    fun metadata(): Map<String, Any> {
        return metadata
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as WebSearchInformationResult
        return totalResults == that.totalResults
                && pageNumber == that.pageNumber
                && metadata == that.metadata
    }

    override fun hashCode(): Int {
        return Objects.hash(totalResults, pageNumber, metadata)
    }

    override fun toString(): String {
        return "WebSearchInformationResult{" +
                "totalResults=" + totalResults +
                ", pageNumber=" + pageNumber +
                ", metadata=" + metadata +
                '}'
    }

    companion object {
        /**
         * Creates a new WebSearchInformationResult with the specified total results.
         *
         * @param totalResults The total number of results.
         * @return The new WebSearchInformationResult.
         */
        fun from(totalResults: Long): WebSearchInformationResult {
            return WebSearchInformationResult(totalResults)
        }

        /**
         * Creates a new WebSearchInformationResult with the specified total results, page number, and metadata.
         *
         * @param totalResults The total number of results.
         * @param pageNumber  The page number.
         * @param metadata     The metadata.
         * @return The new WebSearchInformationResult.
         */
        fun from(totalResults: Long, pageNumber: Int?, metadata: Map<String, Any>?): WebSearchInformationResult {
            return WebSearchInformationResult(totalResults, pageNumber, metadata)
        }
    }
}
