package dev.langchain4j.data.image

import dev.langchain4j.internal.Utils
import java.net.URI
import java.util.Objects

/**
 * Represents an image as a URL or as a Base64-encoded string.
 */
class Image private constructor(builder: Builder) {
    private val url: URI?
    private val base64Data: String?
    private val mimeType: String?
    private val revisedPrompt: String?

    /**
     * Create a new [Image] from the Builder.
     * @param builder the builder.
     */
    init {
        this.url = builder.url
        this.base64Data = builder.base64Data
        this.mimeType = builder.mimeType
        this.revisedPrompt = builder.revisedPrompt
    }

    /**
     * Get the url of the image.
     * @return the url of the image, or null if not set.
     */
    fun url(): URI? {
        return url
    }

    /**
     * Get the base64 data of the image.
     * @return the base64 data of the image, or null if not set.
     */
    fun base64Data(): String? {
        return base64Data
    }

    /**
     * Get the mime type of the image.
     * @return the mime type of the image, or null if not set.
     */
    fun mimeType(): String? {
        return mimeType
    }

    /**
     * Get the revised prompt of the image.
     * @return the revised prompt of the image, or null if not set.
     */
    fun revisedPrompt(): String? {
        return revisedPrompt
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Image
        return this.url == that.url
                && this.base64Data == that.base64Data
                && this.mimeType == that.mimeType
                && this.revisedPrompt == that.revisedPrompt
    }

    override fun hashCode(): Int {
        return Objects.hash(url, base64Data, mimeType, revisedPrompt)
    }

    override fun toString(): String {
        return "Image {" +
                " url = " + Utils.quoted(url) +
                ", base64Data = " + Utils.quoted(base64Data) +
                ", mimeType = " + Utils.quoted(mimeType) +
                ", revisedPrompt = " + Utils.quoted(revisedPrompt) +
                " }"
    }

    /**
     * Builder for [Image].
     */
    class Builder
    /**
     * Create a new [Builder].
     */
    {
        var url: URI? = null
        var base64Data: String? = null
        var mimeType: String? = null
        var revisedPrompt: String? = null

        /**
         * Set the url of the image.
         * @param url the url of the image.
         * @return `this`
         */
        fun url(url: URI?): Builder {
            this.url = url
            return this
        }

        /**
         * Set the url of the image.
         * @param url the url of the image.
         * @return `this`
         */
        fun url(url: String): Builder {
            return url(URI.create(url))
        }

        /**
         * Set the base64 data of the image.
         * @param base64Data the base64 data of the image.
         * @return `this`
         */
        fun base64Data(base64Data: String?): Builder {
            this.base64Data = base64Data
            return this
        }

        /**
         * Set the mime type of the image.
         * @param mimeType the mime type of the image.
         * @return `this`
         */
        fun mimeType(mimeType: String?): Builder {
            this.mimeType = mimeType
            return this
        }

        /**
         * Set the revised prompt of the image.
         * @param revisedPrompt the revised prompt of the image.
         * @return `this`
         */
        fun revisedPrompt(revisedPrompt: String?): Builder {
            this.revisedPrompt = revisedPrompt
            return this
        }

        /**
         * Build the [Image].
         * @return the [Image].
         */
        fun build(): Image {
            return Image(this)
        }
    }

    companion object {
        /**
         * Create a new [Builder].
         * @return the new [Builder].
         */
        fun builder(): Builder {
            return Builder()
        }
    }
}
