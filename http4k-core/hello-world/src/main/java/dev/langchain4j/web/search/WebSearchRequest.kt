package dev.langchain4j.web.search

import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import java.util.Objects

/**
 * Represents a search request that can be made by the user to perform searches in any implementation of [WebSearchEngine].
 *
 *
 * [WebSearchRequest] follow opensearch foundation standard implemented by most web search engine libs like Google, Bing, Yahoo, etc.
 * [OpenSearch#parameters](https://github.com/dewitt/opensearch/blob/master/opensearch-1-1-draft-6.md#opensearch-11-parameters)
 *
 *
 *
 * The [.searchTerms] are the keywords that the search client desires to search for. This param is mandatory to perform a search.
 *
 *
 * <br></br>
 * Configurable parameters (optional):
 *
 *  * [.maxResults] - The expected number of results to be found if the search request were made. Each search engine may have a different limit for the maximum number of results that can be returned.
 *  * [.language] - The desired language for search results is a string that indicates that the search client desires search results in the specified language. Each search engine may have a different set of supported languages.
 *  * [.geoLocation] - The desired geolocation for search results is a string that indicates that the search client desires search results in the specified geolocation. Each search engine may have a different set of supported geolocations.
 *  * [.startPage] - The start page number for search results is the page number of the set of search results desired by the search user.
 *  * [.startIndex] - The start index for search results is the index of the first search result desired by the search user. Each search engine may have a different set of supported start indexes in combination with the start page number.
 *  * [.safeSearch] - The safe search flag is a boolean that indicates that the search client desires search results with safe search enabled or disabled.
 *  * [.additionalParams] - The additional parameters for the search request are a map of key-value pairs that represent additional parameters for the search request. It's a way to be flex and add custom param for each search engine.
 *
 */
class WebSearchRequest private constructor(builder: Builder) {
    private val searchTerms: String
    private val maxResults: Int?
    private val language: String?
    private val geoLocation: String?
    private val startPage: Int
    private val startIndex: Int?
    private val safeSearch: Boolean
    private val additionalParams: Map<String, Any>

    init {
        this.searchTerms = ValidationUtils.ensureNotBlank(builder.searchTerms, "searchTerms")
        this.maxResults = builder.maxResults
        this.language = builder.language
        this.geoLocation = builder.geoLocation
        this.startPage = Utils.getOrDefault(builder.startPage, 1)!!
        this.startIndex = builder.startIndex
        this.safeSearch = Utils.getOrDefault(builder.safeSearch, true)
        this.additionalParams = Utils.copy(builder.additionalParams)
    }

    /**
     * Get the search terms.
     *
     * @return The search terms.
     */
    fun searchTerms(): String {
        return searchTerms
    }

    /**
     * Get the maximum number of results.
     *
     * @return The maximum number of results.
     */
    fun maxResults(): Int? {
        return maxResults
    }

    /**
     * Get the desired language for search results.
     *
     * @return The desired language for search results.
     */
    fun language(): String? {
        return language
    }

    /**
     * Get the desired geolocation for search results.
     *
     * @return The desired geolocation for search results.
     */
    fun geoLocation(): String? {
        return geoLocation
    }

    /**
     * Get the start page number for search results.
     *
     * @return The start page number for search results.
     */
    fun startPage(): Int {
        return startPage
    }

    /**
     * Get the start index for search results.
     *
     * @return The start index for search results.
     */
    fun startIndex(): Int? {
        return startIndex
    }

    /**
     * Get the safe search flag.
     *
     * @return The safe search flag.
     */
    fun safeSearch(): Boolean {
        return safeSearch
    }

    /**
     * Get the additional parameters for the search request.
     *
     * @return The additional parameters for the search request.
     */
    fun additionalParams(): Map<String, Any> {
        return additionalParams
    }

    override fun equals(another: Any?): Boolean {
        if (this === another) return true
        return another is WebSearchRequest
                && equalTo(another)
    }

    private fun equalTo(another: WebSearchRequest): Boolean {
        return searchTerms == another.searchTerms
                && maxResults == another.maxResults
                && language == another.language
                && geoLocation == another.geoLocation
                && startPage == another.startPage
                && startIndex == another.startIndex
                && safeSearch == another.safeSearch
                && additionalParams == another.additionalParams
    }

    override fun hashCode(): Int {
        var h = 5381
        h += (h shl 5) + Objects.hashCode(searchTerms)
        h += (h shl 5) + Objects.hashCode(maxResults)
        h += (h shl 5) + Objects.hashCode(language)
        h += (h shl 5) + Objects.hashCode(geoLocation)
        h += (h shl 5) + Objects.hashCode(startPage)
        h += (h shl 5) + Objects.hashCode(startIndex)
        h += (h shl 5) + Objects.hashCode(safeSearch)
        h += (h shl 5) + Objects.hashCode(additionalParams)
        return h
    }

    override fun toString(): String {
        return "WebSearchRequest{" +
                "searchTerms='" + searchTerms + '\'' +
                ", maxResults=" + maxResults +
                ", language='" + language + '\'' +
                ", geoLocation='" + geoLocation + '\'' +
                ", startPage=" + startPage +
                ", startIndex=" + startIndex +
                ", siteRestrict=" + safeSearch +
                ", additionalParams=" + additionalParams +
                '}'
    }

    class Builder {
        var searchTerms: String? = null
        var maxResults: Int? = null
        var language: String? = null
        var geoLocation: String? = null
        var startPage: Int? = null
        var startIndex: Int? = null
        var safeSearch: Boolean? = null
        var additionalParams: Map<String, Any>? = null

        /**
         * Set the search terms.
         *
         * @param searchTerms The keyword or keywords desired by the search user.
         * @return The builder instance.
         */
        fun searchTerms(searchTerms: String?): Builder {
            this.searchTerms = searchTerms
            return this
        }

        /**
         * Set the maximum number of results.
         *
         * @param maxResults The maximum number of results.
         * @return The builder instance.
         */
        fun maxResults(maxResults: Int?): Builder {
            this.maxResults = maxResults
            return this
        }

        /**
         * Set the desired language for search results.
         *
         * @param language The desired language for search results.
         * @return The builder instance.
         */
        fun language(language: String?): Builder {
            this.language = language
            return this
        }

        /**
         * Set the desired geolocation for search results.
         *
         * @param geoLocation The desired geolocation for search results.
         * @return The builder instance.
         */
        fun geoLocation(geoLocation: String?): Builder {
            this.geoLocation = geoLocation
            return this
        }

        /**
         * Set the start page number for search results.
         *
         * @param startPage The start page number for search results.
         * @return The builder instance.
         */
        fun startPage(startPage: Int?): Builder {
            this.startPage = startPage
            return this
        }

        /**
         * Set the start index for search results.
         *
         * @param startIndex The start index for search results.
         * @return The builder instance.
         */
        fun startIndex(startIndex: Int?): Builder {
            this.startIndex = startIndex
            return this
        }

        /**
         * Set the safe search flag.
         *
         * @param safeSearch The safe search flag.
         * @return The builder instance.
         */
        fun safeSearch(safeSearch: Boolean?): Builder {
            this.safeSearch = safeSearch
            return this
        }

        /**
         * Set the additional parameters for the search request.
         *
         * @param additionalParams The additional parameters for the search request.
         * @return The builder instance.
         */
        fun additionalParams(additionalParams: Map<String, Any>?): Builder {
            this.additionalParams = additionalParams
            return this
        }

        /**
         * Build the web search request.
         *
         * @return The web search request.
         */
        fun build(): WebSearchRequest {
            return WebSearchRequest(this)
        }
    }

    companion object {
        /**
         * Create a new builder instance.
         *
         * @return A new builder instance.
         */
        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }

        /**
         * Create a web search request with the given search terms.
         *
         * @param searchTerms The search terms.
         * @return The web search request.
         */
        fun from(searchTerms: String?): WebSearchRequest {
            return builder().searchTerms(searchTerms).build()
        }

        /**
         * Create a web search request with the given search terms and maximum number of results.
         *
         * @param searchTerms The search terms.
         * @param maxResults The maximum number of results.
         * @return The web search request.
         */
        fun from(searchTerms: String?, maxResults: Int?): WebSearchRequest {
            return builder().searchTerms(searchTerms).maxResults(maxResults).build()
        }
    }
}
