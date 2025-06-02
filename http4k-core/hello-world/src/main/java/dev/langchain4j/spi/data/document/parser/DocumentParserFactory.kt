package dev.langchain4j.spi.data.document.parser

import dev.langchain4j.data.document.DocumentParser

/**
 * A factory for creating [DocumentParser] instances through SPI.
 * <br></br>
 * Available implementations: `ApacheTikaDocumentParserFactory`
 * in the `langchain4j-document-parser-apache-tika` module.
 * For the "Easy RAG", import `langchain4j-easy-rag` module.
 */
interface DocumentParserFactory {
    fun create(): DocumentParser?
}
