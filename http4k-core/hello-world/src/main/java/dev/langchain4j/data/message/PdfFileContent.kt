package dev.langchain4j.data.message

import dev.langchain4j.data.pdf.PdfFile
import dev.langchain4j.internal.ValidationUtils
import java.net.URI
import java.util.Objects

class PdfFileContent : Content {
    private val pdfFile: PdfFile

    override fun type(): ContentType {
        return ContentType.PDF
    }

    /**
     * Create a new [PdfFileContent] from the given url.
     *
     * @param url the url of the PDF.
     */
    constructor(url: URI) {
        this.pdfFile = PdfFile.Companion.builder()
            .url(ValidationUtils.ensureNotNull<URI>(url, "url"))
            .build()
    }

    /**
     * Create a new [PdfFileContent] from the given url.
     *
     * @param url the url of the PDF.
     */
    constructor(url: String) : this(URI.create(url))

    /**
     * Create a new [PdfFileContent] from the given base64 data and mime type.
     *
     * @param base64Data the base64 data of the PDF.
     * @param mimeType   the mime type of the PDF.
     */
    constructor(base64Data: String?, mimeType: String?) {
        this.pdfFile = PdfFile.Companion.builder()
            .base64Data(ValidationUtils.ensureNotBlank(base64Data, "base64data"))
            .mimeType(mimeType)
            .build()
    }

    /**
     * Create a new [PdfFileContent] from the given PDF file.
     *
     * @param pdfFile the PDF.
     */
    constructor(pdfFile: PdfFile) {
        this.pdfFile = pdfFile
    }

    /**
     * Get the `PdfFile`.
     *
     * @return the `PdfFile`.
     */
    fun pdfFile(): PdfFile {
        return pdfFile
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as PdfFileContent
        return this.pdfFile == that.pdfFile
    }

    override fun hashCode(): Int {
        return Objects.hash(pdfFile)
    }

    override fun toString(): String {
        return "PdfFileContent {" +
                " pdfFile = " + pdfFile +
                " }"
    }

    companion object {
        /**
         * Create a new [PdfFileContent] from the given url.
         *
         * @param url the url of the PDF.
         * @return the new [PdfFileContent].
         */
        fun from(url: URI): PdfFileContent {
            return PdfFileContent(url)
        }

        /**
         * Create a new [PdfFileContent] from the given url.
         *
         * @param url the url of the PDF.
         * @return the new [PdfFileContent].
         */
        fun from(url: String): PdfFileContent {
            return PdfFileContent(url)
        }

        /**
         * Create a new [PdfFileContent] from the given base64 data and mime type.
         *
         * @param base64Data the base64 data of the PDF.
         * @param mimeType   the mime type of the PDF.
         * @return the new [PdfFileContent].
         */
        fun from(base64Data: String?, mimeType: String?): PdfFileContent {
            return PdfFileContent(base64Data, mimeType)
        }

        /**
         * Create a new [PdfFileContent] from the given PDF.
         *
         * @param pdfFile the PDF.
         * @return the new [PdfFileContent].
         */
        fun from(pdfFile: PdfFile): PdfFileContent {
            return PdfFileContent(pdfFile)
        }
    }
}
