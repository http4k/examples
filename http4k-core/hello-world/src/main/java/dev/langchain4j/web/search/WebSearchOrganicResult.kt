package dev.langchain4j.web.search

import dev.langchain4j.data.document.Document
import dev.langchain4j.data.document.Metadata
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.internal.Utils
import java.net.URI
import java.util.Objects

/**
 * Represents an organic search results are the web pages that are returned by the search engine in response to a search query.
 * This includes the title, URL, snippet and/or content, and metadata of the web page.
 *
 *
 * These results are typically ranked by relevance to the search query.
 *
 *
 */
class WebSearchOrganicResult {
    private val title: String
    private val url: URI
    private val snippet: String?
    private val content: String?
    private val metadata: Map<String, String>

    /**
     * Constructs a WebSearchOrganicResult object with the given title and URL.
     *
     * @param title The title of the search result.
     * @param url The URL associated with the search result.
     */
    constructor(title: String?, url: URI) {
        this.title = title!!
        this.url = url!!
        this.snippet = null
        this.content = null
        this.metadata = java.util.Map.of()
    }

    /**
     * Constructs a WebSearchOrganicResult object with the given title, URL, snippet and/or content.
     *
     * @param title   The title of the search result.
     * @param url    The URL associated with the search result.
     * @param snippet The snippet of the search result, in plain text.
     * @param content The most query related content from the scraped url.
     */
    constructor(title: String?, url: URI, snippet: String?, content: String?) {
        this.title = title!!
        this.url = url!!
        this.snippet = snippet
        this.content = content
        this.metadata = java.util.Map.of()
    }

    /**
     * Constructs a WebSearchOrganicResult object with the given title, URL, snippet and/or content, and metadata.
     *
     * @param title           The title of the search result.
     * @param url             The URL associated with the search result.
     * @param snippet         The snippet of the search result, in plain text.
     * @param content The most query related content from the scraped url.
     * @param metadata  The metadata associated with the search result.
     */
    constructor(title: String?, url: URI, snippet: String?, content: String?, metadata: Map<String, String>?) {
        this.title = title!!
        this.url = url!!
        this.snippet = snippet
        this.content = content
        this.metadata = Utils.copy(metadata)
    }

    /**
     * Returns the title of the web page.
     *
     * @return The title of the web page.
     */
    fun title(): String {
        return title
    }

    /**
     * Returns the URL associated with the web page.
     *
     * @return The URL associated with the web page.
     */
    fun url(): URI {
        return url
    }

    /**
     * Returns the snippet associated with the web page.
     *
     * @return The snippet associated with the web page.
     */
    fun snippet(): String? {
        return snippet
    }

    /**
     * Returns the content scraped from the web page.
     *
     * @return The content scraped from the web page.
     */
    fun content(): String? {
        return content
    }

    /**
     * Returns the result metadata associated with the search result.
     *
     * @return The result metadata associated with the search result.
     */
    fun metadata(): Map<String, String> {
        return metadata
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as WebSearchOrganicResult
        return title == that.title
                && url == that.url
                && snippet == that.snippet
                && content == that.content
                && metadata == that.metadata
    }

    override fun hashCode(): Int {
        return Objects.hash(title, url, snippet, content, metadata)
    }

    override fun toString(): String {
        return "WebSearchOrganicResult{" +
                "title='" + title + '\'' +
                ", url=" + url +
                ", snippet='" + snippet + '\'' +
                ", content='" + content + '\'' +
                ", metadata=" + metadata +
                '}'
    }

    /**
     * Converts this WebSearchOrganicResult to a TextSegment.
     *
     * @return The TextSegment representation of this WebSearchOrganicResult.
     */
    fun toTextSegment(): TextSegment {
        return TextSegment.from(copyToText(), copyToMetadata())
    }

    /**
     * Converts this WebSearchOrganicResult to a Document.
     *
     * @return The Document representation of this WebSearchOrganicResult.
     */
    fun toDocument(): Document {
        return Document.from(copyToText(), copyToMetadata())
    }

    private fun copyToText(): String {
        val text = StringBuilder()
        text.append(title)
        text.append("\n")
        if (Utils.isNotNullOrBlank(content)) {
            text.append(content)
        } else if (Utils.isNotNullOrBlank(snippet)) {
            text.append(snippet)
        }
        return text.toString()
    }

    private fun copyToMetadata(): Metadata {
        val docMetadata = Metadata()
        docMetadata.put("url", url.toString())
        if (metadata != null) {
            for ((key, value) in metadata) {
                docMetadata.put(key, value)
            }
        }
        return docMetadata
    }

    companion object {
        /**
         * Creates a WebSearchOrganicResult object from the given title and URL.
         *
         * @param title   The title of the search result.
         * @param url    The URL associated with the search result.
         * @return The created WebSearchOrganicResult object.
         */
        fun from(title: String?, url: URI): WebSearchOrganicResult {
            return WebSearchOrganicResult(title, url)
        }

        /**
         * Creates a WebSearchOrganicResult object from the given title, URL, snippet and/or content.
         *
         * @param title   The title of the search result.
         * @param url    The URL associated with the search result.
         * @param snippet The snippet of the search result, in plain text.
         * @param content The most query related content from the scraped url.
         * @return The created WebSearchOrganicResult object.
         */
        fun from(title: String?, url: URI, snippet: String?, content: String?): WebSearchOrganicResult {
            return WebSearchOrganicResult(title, url, snippet, content)
        }

        /**
         * Creates a WebSearchOrganicResult object from the given title, URL, snippet and/or content, and result metadata.
         *
         * @param title           The title of the search result.
         * @param url            The URL associated with the search result.
         * @param snippet         The snippet of the search result, in plain text.
         * @param content The most query related content from the scraped url.
         * @param metadata  The metadata associated with the search result.
         * @return The created WebSearchOrganicResult object.
         */
        fun from(
            title: String?,
            url: URI,
            snippet: String?,
            content: String?,
            metadata: Map<String, String>?
        ): WebSearchOrganicResult {
            return WebSearchOrganicResult(title, url, snippet, content, metadata)
        }
    }
}
