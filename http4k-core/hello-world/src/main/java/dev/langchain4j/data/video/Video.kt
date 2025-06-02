package dev.langchain4j.data.video

import dev.langchain4j.internal.Utils
import java.net.URI
import java.util.Objects

class Video private constructor(builder: Builder) {
    private val url: URI?
    private val base64Data: String?
    private val mimeType: String?

    /**
     * Create a new [Video] from the Builder.
     *
     * @param builder the builder.
     */
    init {
        this.url = builder.url
        this.base64Data = builder.base64Data
        this.mimeType = builder.mimeType
    }

    /**
     * Get the url of the video.
     *
     * @return the url of the video, or null if not set.
     */
    fun url(): URI? {
        return url
    }

    /**
     * Get the base64 data of the video.
     *
     * @return the base64 data of the video, or null if not set.
     */
    fun base64Data(): String? {
        return base64Data
    }

    /**
     * Get the mime type of the video.
     *
     * @return the mime type of the video, or null if not set.
     */
    fun mimeType(): String? {
        return mimeType
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Video
        return this.url == that.url
                && this.base64Data == that.base64Data
                && this.mimeType == that.mimeType
    }

    override fun hashCode(): Int {
        return Objects.hash(url, base64Data, mimeType)
    }

    override fun toString(): String {
        return "Video {" +
                " url = " + Utils.quoted(url) +
                ", base64Data = " + Utils.quoted(base64Data) +
                ", mimeType = " + Utils.quoted(mimeType) +
                " }"
    }

    /**
     * Builder for [Video].
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
         * Set the url of the video.
         *
         * @param url the url of the video.
         * @return `this`
         */
        fun url(url: URI?): Builder {
            this.url = url
            return this
        }

        /**
         * Set the url of the video.
         *
         * @param url the url of the video.
         * @return `this`
         */
        fun url(url: String): Builder {
            return url(URI.create(url))
        }

        /**
         * Set the base64 data of the video.
         *
         * @param base64Data the base64 data of the video.
         * @return `this`
         */
        fun base64Data(base64Data: String?): Builder {
            this.base64Data = base64Data
            return this
        }

        /**
         * Set the mime type of the video.
         *
         * @param mimeType the mime type of the video.
         * @return `this`
         */
        fun mimeType(mimeType: String?): Builder {
            this.mimeType = mimeType
            return this
        }

        /**
         * Build the [Video].
         *
         * @return the [Video].
         */
        fun build(): Video {
            return Video(this)
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
