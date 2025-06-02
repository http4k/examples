package dev.langchain4j.data.document

import dev.langchain4j.data.segment.TextSegment
import java.util.stream.Collectors

/**
 * Defines the interface for splitting a document into text segments.
 * This is necessary as LLMs have a limited context window, making it impossible to send the entire document at once.
 * Therefore, the document should first be split into segments, and only the relevant segments should be sent to LLM.
 * <br></br>
 * `DocumentSplitters.recursive()` from a `dev.langchain4j:langchain4j` module is a good starting point.
 */
interface DocumentSplitter {
    /**
     * Splits a single Document into a list of TextSegment objects.
     * The metadata is typically copied from the document and enriched with segment-specific information,
     * such as position in the document, page number, etc.
     *
     * @param document The Document to be split.
     * @return A list of TextSegment objects derived from the input Document.
     */
    fun split(document: Document?): List<TextSegment?>

    /**
     * Splits a list of Documents into a list of TextSegment objects.
     * This is a convenience method that calls the split method for each Document in the list.
     *
     * @param documents The list of Documents to be split.
     * @return A list of TextSegment objects derived from the input Documents.
     */
    fun splitAll(documents: List<Document?>): List<TextSegment?> {
        return documents.stream()
            .flatMap { document: Document? -> split(document).stream() }
            .collect(Collectors.toList())
    }
}
