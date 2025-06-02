package dev.langchain4j.data.pdf

import dev.langchain4j.data.pdf.PdfFile
import dev.langchain4j.internal.Utils
import java.net.URI
import java.util.Objects

class PdfFile private constructor(builder: Builder) {
    private val url: URI?
    private val base64Data: String?
    private val mimeType: String

    /**
     * Create a new [PdfFile] from the Builder.
     *
     * @param builder the builder.
     */
    init {
        this.url = builder.url
        this.base64Data = builder.base64Data
        this.mimeType = Utils.getOrDefault(builder.mimeType, "application/pdf")
    }

    /**
     * Get the url of the PDF.
     *
     * @return the url of the PDF, or null if not set.
     */
    fun url(): URI? {
        return url
    }

    /**
     * Get the base64 data of the rich format document.
     *
     * @return the base64 data of the rich format document, or null if not set.
     */
    fun base64Data(): String? {
        return base64Data
    }

    /**
     * Get the mime type of the rich format document.
     *
     * @return the mime type of the rich format document.
     */
    fun mimeType(): String {
        return mimeType
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as PdfFile
        return this.url == that.url
                && this.base64Data == that.base64Data
                && this.mimeType == that.mimeType
    }

    override fun hashCode(): Int {
        return Objects.hash(url, base64Data, mimeType)
    }

    override fun toString(): String {
        return "PdfFile {" +
                " url = " + Utils.quoted(url) +
                ", base64Data = " + Utils.quoted(base64Data) +
                ", mimeType = " + Utils.quoted(mimeType) +
                " }"
    }

    /**
     * Builder for [PdfFile].
     */
    class Builder
    /**
     * Create a new [Builder].
     */
    {
        var url: URI? = null
        var base64Data: String? = null
        var mimeType: String? = null

        /**
         * Set the url of the PDF document.
         *
         * @param url the url of the PDF document.
         * @return `this`
         */
        fun url(url: URI?): Builder {
            this.url = url
            return this
        }

        /**
         * Set the url of the PDF document.
         *
         * @param url the url of the PDF document.
         * @return `this`
         */
        fun url(url: String): Builder {
            return url(URI.create(url))
        }

        /**
         * Set the base64 data of the PDF document.
         *
         * @param base64Data the base64 data of the PDF document.
         * @return `this`
         */
        fun base64Data(base64Data: String?): Builder {
            this.base64Data = base64Data
            return this
        }

        /**
         * Set the mime type of the PDF document.
         *
         * @param mimeType the mime type of the PDF document.
         * @return `this`
         */
        fun mimeType(mimeType: String?): Builder {
            this.mimeType = mimeType
            return this
        }

        /**
         * Build the [PdfFile].
         *
         * @return the [PdfFile].
         */
        fun build(): PdfFile {
            return PdfFile(this)
        }
    }

    companion object {
        /**
         * Create a new [Builder].
         *
         * @return the new [Builder].
         */
        fun builder(): Builder {
            return Builder()
        }
    }
}
