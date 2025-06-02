package dev.langchain4j.data.document

import java.io.InputStream

/**
 * Defines the interface for parsing an [InputStream] into a [Document].
 * Different document types require specialized parsing logic.
 */
interface DocumentParser {
    /**
     * Parses a given [InputStream] into a [Document].
     * The specific implementation of this method will depend on the type of the document being parsed.
     *
     *
     * Note: This method does not close the provided [InputStream] - it is the
     * caller's responsibility to manage the lifecycle of the stream.
     *
     * @param inputStream The [InputStream] that contains the content of the [Document].
     * @return The parsed [Document].
     * @throws BlankDocumentException when the parsed [Document] is blank/empty.
     */
    fun parse(inputStream: InputStream?): Document?
}
