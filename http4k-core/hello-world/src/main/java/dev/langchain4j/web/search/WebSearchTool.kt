package dev.langchain4j.web.search

import dev.langchain4j.agent.tool.P
import dev.langchain4j.agent.tool.Tool
import dev.langchain4j.internal.Utils
import java.util.stream.Collectors

class WebSearchTool(searchEngine: WebSearchEngine) {
    private val searchEngine: WebSearchEngine =
        searchEngine!!

    /**
     * Runs a search query on the web search engine and returns a pretty-string representation of the search results.
     *
     * @param query the search user query
     * @return a pretty-string representation of the search results
     */
    @Tool("This tool can be used to perform web searches using search engines such as Google, particularly when seeking information about recent events.")
    fun searchWeb(@P("Web search query") query: String?): String {
        val results = searchEngine.search(query)
        return format(results!!)
    }

    private fun format(results: WebSearchResults): String {
        if (Utils.isNullOrEmpty(results.results())) return "No results found."

        return results.results()
            .stream()
            .map { organicResult: WebSearchOrganicResult? ->
                ("""
    Title: ${organicResult!!.title()}
    Source: ${organicResult.url()}
    
    """.trimIndent()
                        + (if (organicResult.content() != null) """
     Content:
     ${organicResult.content()}
     """.trimIndent() else """
     Snippet:
     ${organicResult.snippet()}
     """.trimIndent()))
            }
            .collect(Collectors.joining("\n\n"))
    }

    companion object {
        /**
         * Creates a new WebSearchTool with the specified web search engine.
         *
         * @param searchEngine the web search engine to use for searching the web
         * @return a new WebSearchTool
         */
        fun from(searchEngine: WebSearchEngine): WebSearchTool {
            return WebSearchTool(searchEngine)
        }
    }
}
