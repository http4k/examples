package dev.langchain4j.data.message

import dev.langchain4j.data.audio.Audio
import dev.langchain4j.internal.ValidationUtils
import java.net.URI
import java.util.Objects

class AudioContent : Content {
    private val audio: Audio

    override fun type(): ContentType {
        return ContentType.AUDIO
    }

    /**
     * Create a new [AudioContent] from the given url.
     *
     * @param url the url of the Audio.
     */
    constructor(url: URI) {
        this.audio = Audio.Companion.builder()
            .url(ValidationUtils.ensureNotNull<URI>(url, "url"))
            .build()
    }

    /**
     * Create a new [AudioContent] from the given url.
     *
     * @param url the url of the Audio.
     */
    constructor(url: String) : this(URI.create(url))

    /**
     * Create a new [AudioContent] from the given base64 data and mime type.
     *
     * @param base64Data the base64 data of the Audio.
     * @param mimeType   the mime type of the Audio.
     */
    constructor(base64Data: String?, mimeType: String?) {
        this.audio = Audio.Companion.builder()
            .base64Data(ValidationUtils.ensureNotBlank(base64Data, "base64data"))
            .mimeType(ValidationUtils.ensureNotBlank(mimeType, "mimeType")).build()
    }

    /**
     * Create a new [AudioContent] from the given Audio.
     *
     * @param audio the audio.
     */
    constructor(audio: Audio) {
        this.audio = audio
    }

    /**
     * Get the `Audio`.
     *
     * @return the `Audio`.
     */
    fun audio(): Audio {
        return audio
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as AudioContent
        return this.audio == that.audio
    }

    override fun hashCode(): Int {
        return Objects.hash(audio)
    }

    override fun toString(): String {
        return "AudioContent {" +
                " audio = " + audio +
                " }"
    }

    companion object {
        /**
         * Create a new [AudioContent] from the given url.
         *
         * @param url the url of the Audio.
         * @return the new [AudioContent].
         */
        fun from(url: URI): AudioContent {
            return AudioContent(url)
        }

        /**
         * Create a new [AudioContent] from the given url.
         *
         * @param url the url of the Audio.
         * @return the new [AudioContent].
         */
        fun from(url: String): AudioContent {
            return AudioContent(url)
        }

        /**
         * Create a new [AudioContent] from the given base64 data and mime type.
         *
         * @param base64Data the base64 data of the Audio.
         * @param mimeType   the mime type of the Audio.
         * @return the new [AudioContent].
         */
        fun from(base64Data: String?, mimeType: String?): AudioContent {
            return AudioContent(base64Data, mimeType)
        }

        /**
         * Create a new [AudioContent] from the given Audio.
         *
         * @param audio the Audio.
         * @return the new [AudioContent].
         */
        fun from(audio: Audio): AudioContent {
            return AudioContent(audio)
        }
    }
}
