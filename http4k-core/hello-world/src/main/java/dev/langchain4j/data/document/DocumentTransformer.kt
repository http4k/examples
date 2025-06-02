package dev.langchain4j.data.document

import java.util.Objects
import java.util.stream.Collectors

/**
 * Defines the interface for transforming a [Document].
 * Implementations can perform a variety of tasks such as transforming, filtering, enriching, etc.
 */
interface DocumentTransformer {
    /**
     * Transforms a provided document.
     *
     * @param document The document to be transformed.
     * @return The transformed document, or null if the document should be filtered out.
     */
    fun transform(document: Document?): Document?

    /**
     * Transforms all the provided documents.
     *
     * @param documents A list of documents to be transformed.
     * @return A list of transformed documents. The length of this list may be shorter or longer than the original list. Returns an empty list if all documents were filtered out.
     */
    fun transformAll(documents: List<Document?>): List<Document?> {
        return documents.stream()
            .map { document: Document? -> this.transform(document) }
            .filter { obj: Document? -> Objects.nonNull(obj) }
            .collect(Collectors.toList())
    }
}
