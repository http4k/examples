package dev.langchain4j.data.document

import dev.langchain4j.data.segment.TextSegment

/**
 * Represents an unstructured piece of text that usually corresponds to a content of a single file.
 * This text could originate from various sources such as a text file, PDF, DOCX, or a web page (HTML).
 * Each document may have associated [Metadata] including its source, owner, creation date, etc.
 */
interface Document {
    /**
     * Returns the text of this document.
     *
     * @return the text.
     */
    fun text(): String?

    /**
     * Returns the metadata associated with this document.
     *
     * @return the metadata.
     */
    fun metadata(): Metadata

    /**
     * Builds a [TextSegment] from this document.
     *
     * @return a [TextSegment]
     */
    fun toTextSegment(): TextSegment {
        return if (metadata().containsKey("index")) {
            TextSegment.Companion.from(text(), metadata().copy())
        } else {
            TextSegment.Companion.from(text(), metadata().copy().put("index", "0"))
        }
    }

    companion object {
        /**
         * Creates a new Document from the given text.
         *
         *
         * The created document will have empty metadata.
         *
         * @param text the text of the document.
         * @return a new Document.
         */
        fun from(text: String?): Document {
            return DefaultDocument(text)
        }

        /**
         * Creates a new Document from the given text.
         *
         * @param text     the text of the document.
         * @param metadata the metadata of the document.
         * @return a new Document.
         */
        fun from(text: String?, metadata: Metadata): Document {
            return DefaultDocument(text, metadata)
        }

        /**
         * Creates a new Document from the given text.
         *
         *
         * The created document will have empty metadata.
         *
         * @param text the text of the document.
         * @return a new Document.
         */
        fun document(text: String?): Document {
            return from(text)
        }

        /**
         * Creates a new Document from the given text.
         *
         * @param text     the text of the document.
         * @param metadata the metadata of the document.
         * @return a new Document.
         */
        fun document(text: String?, metadata: Metadata): Document {
            return from(text, metadata)
        }

        /**
         * Common metadata key for the name of the file from which the document was loaded.
         */
        const val FILE_NAME: String = "file_name"

        /**
         * Common metadata key for the absolute path of the directory from which the document was loaded.
         */
        const val ABSOLUTE_DIRECTORY_PATH: String = "absolute_directory_path"

        /**
         * Common metadata key for the URL from which the document was loaded.
         */
        const val URL: String = "url"
    }
}
