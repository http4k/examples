package dev.langchain4j.data.message

import dev.langchain4j.data.video.Video
import dev.langchain4j.internal.ValidationUtils
import java.net.URI
import java.util.Objects

class VideoContent : Content {
    private val video: Video

    override fun type(): ContentType {
        return ContentType.VIDEO
    }

    /**
     * Create a new [VideoContent] from the given url.
     *
     * @param url the url of the Video.
     */
    constructor(url: URI) {
        this.video = Video.Companion.builder()
            .url(ValidationUtils.ensureNotNull<URI>(url, "url"))
            .build()
    }

    /**
     * Create a new [VideoContent] from the given url.
     *
     * @param url the url of the video.
     */
    constructor(url: String) : this(URI.create(url))

    /**
     * Create a new [VideoContent] from the given base64 data and mime type.
     *
     * @param base64Data the base64 data of the video.
     * @param mimeType   the mime type of the video.
     */
    constructor(base64Data: String?, mimeType: String?) {
        this.video = Video.Companion.builder()
            .base64Data(ValidationUtils.ensureNotBlank(base64Data, "base64data"))
            .mimeType(ValidationUtils.ensureNotBlank(mimeType, "mimeType")).build()
    }

    /**
     * Create a new [VideoContent] from the given video.
     *
     * @param video the video.
     */
    constructor(video: Video) {
        this.video = video
    }

    /**
     * Get the `Video`.
     *
     * @return the `Video`.
     */
    fun video(): Video {
        return video
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as VideoContent
        return this.video == that.video
    }

    override fun hashCode(): Int {
        return Objects.hash(video)
    }

    override fun toString(): String {
        return "VideoContent {" +
                " video = " + video +
                " }"
    }

    companion object {
        /**
         * Create a new [VideoContent] from the given url.
         *
         * @param url the url of the video.
         * @return the new [VideoContent].
         */
        fun from(url: URI): VideoContent {
            return VideoContent(url)
        }

        /**
         * Create a new [VideoContent] from the given url.
         *
         * @param url the url of the video.
         * @return the new [VideoContent].
         */
        fun from(url: String): VideoContent {
            return VideoContent(url)
        }

        /**
         * Create a new [VideoContent] from the given base64 data and mime type.
         *
         * @param base64Data the base64 data of the video.
         * @param mimeType   the mime type of the video.
         * @return the new [VideoContent].
         */
        fun from(base64Data: String?, mimeType: String?): VideoContent {
            return VideoContent(base64Data, mimeType)
        }

        /**
         * Create a new [VideoContent] from the given video.
         *
         * @param video the video.
         * @return the new [VideoContent].
         */
        fun from(video: Video): VideoContent {
            return VideoContent(video)
        }
    }
}
