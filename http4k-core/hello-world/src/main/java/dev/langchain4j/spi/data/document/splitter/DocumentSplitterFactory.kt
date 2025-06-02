package dev.langchain4j.spi.data.document.splitter

import dev.langchain4j.data.document.DocumentSplitter

/**
 * A factory for creating [DocumentSplitter] instances through SPI.
 * <br></br>
 * Available implementations: `RecursiveDocumentSplitterFactory`
 * in the `langchain4j-easy-rag` module.
 */
interface DocumentSplitterFactory {
    fun create(): DocumentSplitter?
}
