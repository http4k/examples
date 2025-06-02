package dev.langchain4j.store.embedding

import dev.langchain4j.data.document.Document
import dev.langchain4j.data.document.DocumentSplitter
import dev.langchain4j.data.document.DocumentTransformer
import dev.langchain4j.data.embedding.Embedding
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.data.segment.TextSegmentTransformer
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.spi.ServiceHelper
import dev.langchain4j.spi.data.document.splitter.DocumentSplitterFactory
import dev.langchain4j.spi.model.embedding.EmbeddingModelFactory
import dev.langchain4j.store.embedding.EmbeddingStore
import java.util.Arrays
import java.util.stream.Collectors

/**
 * The `EmbeddingStoreIngestor` represents an ingestion pipeline and is responsible
 * for ingesting [Document]s into an [EmbeddingStore].
 * <br></br>
 * <br></br>
 * In the simplest configuration, `EmbeddingStoreIngestor` embeds provided documents
 * using a provided [EmbeddingModel] and stores them, along with their [Embedding]s
 * in an `EmbeddingStore`.
 * <br></br>
 * <br></br>
 * Optionally, the `EmbeddingStoreIngestor` can transform documents using a provided [DocumentTransformer].
 * This can be useful if you want to clean, enrich, or format documents before embedding them.
 * <br></br>
 * <br></br>
 * Optionally, the `EmbeddingStoreIngestor` can split documents into [TextSegment]s
 * using a provided [DocumentSplitter].
 * This can be useful if documents are big, and you want to split them into smaller segments to improve the quality
 * of similarity searches and reduce the size and cost of a prompt sent to the LLM.
 * <br></br>
 * <br></br>
 * Optionally, the `EmbeddingStoreIngestor` can transform `TextSegment`s using a [TextSegmentTransformer].
 * This can be useful if you want to clean, enrich, or format `TextSegment`s before embedding them.
 * <br></br>
 * Including a document title or a short summary in each `TextSegment` is a common technique
 * to improve the quality of similarity searches.
 */
class EmbeddingStoreIngestor(
    private val documentTransformer: DocumentTransformer?,
    documentSplitter: DocumentSplitter?,
    private val textSegmentTransformer: TextSegmentTransformer?,
    embeddingModel: EmbeddingModel?,
    embeddingStore: EmbeddingStore<TextSegment>?
) {
    private val documentSplitter: DocumentSplitter?
    private val embeddingModel: EmbeddingModel
    private val embeddingStore: EmbeddingStore<TextSegment>

    /**
     * Creates an instance of an `EmbeddingStoreIngestor`.
     *
     * @param documentTransformer    The [DocumentTransformer] to use. Optional.
     * @param documentSplitter       The [DocumentSplitter] to use. Optional.
     * If none is specified, it tries to load one through SPI (see [DocumentSplitterFactory]).
     * @param textSegmentTransformer The [TextSegmentTransformer] to use. Optional.
     * @param embeddingModel         The [EmbeddingModel] to use. Mandatory.
     * If none is specified, it tries to load one through SPI (see [EmbeddingModelFactory]).
     * @param embeddingStore         The [EmbeddingStore] to use. Mandatory.
     */
    init {
        this.documentSplitter = Utils.getOrDefault(
            documentSplitter
        ) { loadDocumentSplitter() }
        this.embeddingModel = embeddingModel!!
        this.embeddingStore = ValidationUtils.ensureNotNull(embeddingStore!!, "embeddingStore")
    }

    /**
     * Ingests a specified document into an [EmbeddingStore] that was specified
     * during the creation of this `EmbeddingStoreIngestor`.
     *
     * @param document the document to ingest.
     * @return result including information related to ingestion process.
     */
    fun ingest(document: Document): IngestionResult {
        return ingest(listOf(document))
    }

    /**
     * Ingests specified documents into an [EmbeddingStore] that was specified
     * during the creation of this `EmbeddingStoreIngestor`.
     *
     * @param documents the documents to ingest.
     * @return result including information related to ingestion process.
     */
    fun ingest(vararg documents: Document?): IngestionResult {
        return TODO()
//        return ingest(Arrays.asList(*documents))
    }

    /**
     * Ingests specified documents into an [EmbeddingStore] that was specified
     * during the creation of this `EmbeddingStoreIngestor`.
     *
     * @param documents the documents to ingest.
     * @return result including information related to ingestion process.
     */
    fun ingest(documents: List<Document>): IngestionResult {
        var documents = documents
        var segments: List<TextSegment?>?
        segments = if (documentSplitter != null) {
            documentSplitter.splitAll(documents)
        } else {
            documents.stream()
                .map { obj: Document -> obj.toTextSegment() }
                .collect(Collectors.toList())
        }
        if (textSegmentTransformer != null) {
//            segments = textSegmentTransformer.transformAll(segments)
        }

        val embeddingsResponse = embeddingModel.embedAll(segments)

//        embeddingStore.addAll(embeddingsResponse.content(), segments)

        TODO()
//        return IngestionResult(embeddingsResponse.tokenUsage())
    }

    /**
     * EmbeddingStoreIngestor builder.
     */
    class Builder
    /**
     * Creates a new EmbeddingStoreIngestor builder.
     */
    {
        private var documentTransformer: DocumentTransformer? = null
        private var documentSplitter: DocumentSplitter? = null
        private var textSegmentTransformer: TextSegmentTransformer? = null
        private var embeddingModel: EmbeddingModel? = null
        private var embeddingStore: EmbeddingStore<TextSegment>? = null

        /**
         * Sets the document transformer. Optional.
         *
         * @param documentTransformer the document transformer.
         * @return `this`
         */
        fun documentTransformer(documentTransformer: DocumentTransformer?): Builder {
            this.documentTransformer = documentTransformer
            return this
        }

        /**
         * Sets the document splitter. Optional.
         * If none is specified, it tries to load one through SPI (see [DocumentSplitterFactory]).
         * <br></br>
         * `DocumentSplitters.recursive()` from main (`langchain4j`) module is a good starting point.
         *
         * @param documentSplitter the document splitter.
         * @return `this`
         */
        fun documentSplitter(documentSplitter: DocumentSplitter?): Builder {
            this.documentSplitter = documentSplitter
            return this
        }

        /**
         * Sets the text segment transformer. Optional.
         *
         * @param textSegmentTransformer the text segment transformer.
         * @return `this`
         */
        fun textSegmentTransformer(textSegmentTransformer: TextSegmentTransformer?): Builder {
            this.textSegmentTransformer = textSegmentTransformer
            return this
        }

        /**
         * Sets the embedding model. Mandatory.
         * If none is specified, it tries to load one through SPI (see [EmbeddingModelFactory]).
         *
         * @param embeddingModel the embedding model.
         * @return `this`
         */
        fun embeddingModel(embeddingModel: EmbeddingModel?): Builder {
            this.embeddingModel = embeddingModel
            return this
        }

        /**
         * Sets the embedding store. Mandatory.
         *
         * @param embeddingStore the embedding store.
         * @return `this`
         */
        fun embeddingStore(embeddingStore: EmbeddingStore<TextSegment>?): Builder {
            this.embeddingStore = embeddingStore
            return this
        }

        /**
         * Builds the EmbeddingStoreIngestor.
         *
         * @return the EmbeddingStoreIngestor.
         */
        fun build(): EmbeddingStoreIngestor {
            return EmbeddingStoreIngestor(
                documentTransformer,
                documentSplitter,
                textSegmentTransformer,
                embeddingModel,
                embeddingStore
            )
        }
    }

    companion object {
        private fun loadDocumentSplitter(): DocumentSplitter? {
            val factories = ServiceHelper.loadFactories(
                DocumentSplitterFactory::class.java
            )
            if (factories.size > 1) {
                throw RuntimeException(
                    "Conflict: multiple document splitters have been found in the classpath. " +
                            "Please explicitly specify the one you wish to use."
                )
            }

            for (factory in factories) {
                val documentSplitter = factory.create()
                return documentSplitter
            }

            return null
        }

        private fun loadEmbeddingModel(): EmbeddingModel? {
            val factories = ServiceHelper.loadFactories(
                EmbeddingModelFactory::class.java
            )
            if (factories.size > 1) {
                throw RuntimeException(
                    "Conflict: multiple embedding models have been found in the classpath. " +
                            "Please explicitly specify the one you wish to use."
                )
            }

            for (factory in factories) {
                val embeddingModel = factory.create()
                return embeddingModel
            }

            return null
        }

        /**
         * Ingests a specified [Document] into a specified [EmbeddingStore].
         * <br></br>
         * Uses [DocumentSplitter] and [EmbeddingModel] found through SPIs
         * (see [DocumentSplitterFactory] and [EmbeddingModelFactory]).
         * <br></br>
         * For the "Easy RAG", import `langchain4j-easy-rag` module,
         * which contains a `DocumentSplitterFactory` and `EmbeddingModelFactory` implementations.
         *
         * @return result including information related to ingestion process.
         */
        fun ingest(document: Document, embeddingStore: EmbeddingStore<TextSegment>?): IngestionResult {
            return builder().embeddingStore(embeddingStore).build().ingest(document)
        }

        /**
         * Ingests specified [Document]s into a specified [EmbeddingStore].
         * <br></br>
         * Uses [DocumentSplitter] and [EmbeddingModel] found through SPIs
         * (see [DocumentSplitterFactory] and [EmbeddingModelFactory]).
         * <br></br>
         * For the "Easy RAG", import `langchain4j-easy-rag` module,
         * which contains a `DocumentSplitterFactory` and `EmbeddingModelFactory` implementations.
         *
         * @return result including information related to ingestion process.
         */
        fun ingest(documents: List<Document>, embeddingStore: EmbeddingStore<TextSegment>?): IngestionResult {
            return builder().embeddingStore(embeddingStore).build().ingest(documents)
        }

        /**
         * Creates a new EmbeddingStoreIngestor builder.
         *
         * @return the builder.
         */
        fun builder(): Builder {
            return Builder()
        }
    }
}
