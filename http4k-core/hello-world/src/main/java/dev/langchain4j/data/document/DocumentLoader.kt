package dev.langchain4j.data.document

/**
 * Utility class for loading documents.
 */
object DocumentLoader {
    /**
     * Loads a document from the given source using the given parser.
     *
     *
     * Forwards the source Metadata to the parsed Document.
     *
     * @param source The source from which the document will be loaded.
     * @param parser The parser that will be used to parse the document.
     * @return The loaded document.
     * @throws BlankDocumentException when the parsed [Document] is blank/empty.
     */
    fun load(source: DocumentSource, parser: DocumentParser): Document {
        try {
            source.inputStream().use { inputStream ->
                val document = parser.parse(inputStream)
                document!!.metadata().putAll(source.metadata().toMap())
                return document
            }
        } catch (e: BlankDocumentException) {
            throw e
        } catch (e: Exception) {
            throw RuntimeException("Failed to load document", e)
        }
    }
}
