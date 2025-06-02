package dev.langchain4j.store.embedding.filter

/**
 * Parses a filter expression string into a [Filter] object.
 * <br></br>
 * Currently, there is only one implementation: `SqlFilterParser`
 * in the `langchain4j-embedding-store-filter-parser-sql` module.
 */
interface FilterParser {
    /**
     * Parses a filter expression string into a [Filter] object.
     *
     * @param filter The filter expression as a string.
     * @return A [Filter] object.
     */
    fun parse(filter: String?): Filter?
}
