package dev.langchain4j.data.message

import dev.langchain4j.data.image.Image
import java.net.URI
import java.util.Objects

/**
 * Represents an image with a DetailLevel.
 */
class ImageContent @JvmOverloads constructor(image: Image, detailLevel: DetailLevel = DetailLevel.LOW) :
    Content {
    /**
     * The detail level of an `Image`.
     */
    enum class DetailLevel {
        /**
         * Low detail.
         */
        LOW,

        /**
         * High detail.
         */
        HIGH,

        /**
         * Auto detail.
         */
        AUTO
    }

    private val image: Image =
        image!!
    private val detailLevel: DetailLevel = detailLevel!!

    /**
     * Create a new [ImageContent] from the given url.
     *
     *
     * The image will be created with `DetailLevel.LOW` detail.
     *
     * @param url the url of the image.
     */
    constructor(url: String) : this(URI.create(url))

    /**
     * Create a new [ImageContent] from the given url and detail level.
     *
     * @param url the url of the image.
     * @param detailLevel the detail level of the image.
     */
    /**
     * Create a new [ImageContent] from the given url.
     *
     *
     * The image will be created with `DetailLevel.LOW` detail.
     *
     * @param url the url of the image.
     */
    @JvmOverloads
    constructor(url: URI, detailLevel: DetailLevel = DetailLevel.LOW) : this(
        Image.Companion.builder()
            .url(url!!)
            .build(), detailLevel
    )

    /**
     * Create a new [ImageContent] from the given url and detail level.
     *
     * @param url the url of the image.
     * @param detailLevel the detail level of the image.
     */
    constructor(url: String, detailLevel: DetailLevel) : this(URI.create(url), detailLevel)

    /**
     * Create a new [ImageContent] from the given base64 data and mime type.
     *
     * @param base64Data the base64 data of the image.
     * @param mimeType the mime type of the image.
     * @param detailLevel the detail level of the image.
     */
    /**
     * Create a new [ImageContent] from the given base64 data and mime type.
     *
     *
     * The image will be created with `DetailLevel.LOW` detail.
     *
     * @param base64Data the base64 data of the image.
     * @param mimeType the mime type of the image.
     */
    @JvmOverloads
    constructor(base64Data: String?, mimeType: String?, detailLevel: DetailLevel = DetailLevel.LOW) : this(
        Image.Companion.builder()
            .base64Data(base64Data!!)
            .mimeType(mimeType!!)
            .build(), detailLevel
    )

    /**
     * Create a new [ImageContent] from the given image.
     *
     * @param image the image.
     * @param detailLevel the detail level of the image.
     */

    /**
     * Get the `Image`.
     * @return the `Image`.
     */
    fun image(): Image {
        return image
    }

    /**
     * Get the `DetailLevel`.
     * @return the `DetailLevel`.
     */
    fun detailLevel(): DetailLevel {
        return detailLevel
    }

    override fun type(): ContentType {
        return ContentType.IMAGE
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ImageContent
        return this.image == that.image
                && this.detailLevel == that.detailLevel
    }

    override fun hashCode(): Int {
        return Objects.hash(image, detailLevel)
    }

    override fun toString(): String {
        return "ImageContent {" +
                " image = " + image +
                " detailLevel = " + detailLevel +
                " }"
    }

    companion object {
        /**
         * Create a new [ImageContent] from the given url.
         *
         *
         * The image will be created with `DetailLevel.LOW` detail.
         *
         * @param url the url of the image.
         * @return the new [ImageContent].
         */
        fun from(url: URI): ImageContent {
            return ImageContent(url)
        }

        /**
         * Create a new [ImageContent] from the given url.
         *
         *
         * The image will be created with `DetailLevel.LOW` detail.
         *
         * @param url the url of the image.
         * @return the new [ImageContent].
         */
        fun from(url: String): ImageContent {
            return ImageContent(url)
        }

        /**
         * Create a new [ImageContent] from the given url and detail level.
         *
         * @param url the url of the image.
         * @param detailLevel the detail level of the image.
         * @return the new [ImageContent].
         */
        fun from(url: URI, detailLevel: DetailLevel): ImageContent {
            return ImageContent(url, detailLevel)
        }

        /**
         * Create a new [ImageContent] from the given url and detail level.
         *
         * @param url the url of the image.
         * @param detailLevel the detail level of the image.
         * @return the new [ImageContent].
         */
        fun from(url: String, detailLevel: DetailLevel): ImageContent {
            return ImageContent(url, detailLevel)
        }

        /**
         * Create a new [ImageContent] from the given base64 data and mime type.
         *
         *
         * The image will be created with `DetailLevel.LOW` detail.
         *
         * @param base64Data the base64 data of the image.
         * @param mimeType the mime type of the image.
         * @return the new [ImageContent].
         */
        fun from(base64Data: String?, mimeType: String?): ImageContent {
            return ImageContent(base64Data, mimeType)
        }

        /**
         * Create a new [ImageContent] from the given base64 data and mime type.
         *
         * @param base64Data the base64 data of the image.
         * @param mimeType the mime type of the image.
         * @param detailLevel the detail level of the image.
         * @return the new [ImageContent].
         */
        fun from(base64Data: String?, mimeType: String?, detailLevel: DetailLevel): ImageContent {
            return ImageContent(base64Data, mimeType, detailLevel)
        }

        /**
         * Create a new [ImageContent] from the given image.
         *
         *
         * The image will be created with `DetailLevel.LOW` detail.
         *
         * @param image the image.
         * @return the new [ImageContent].
         */
        fun from(image: Image): ImageContent {
            return ImageContent(image)
        }

        /**
         * Create a new [ImageContent] from the given image.
         *
         * @param image the image.
         * @param detailLevel the detail level of the image.
         * @return the new [ImageContent].
         */
        fun from(image: Image, detailLevel: DetailLevel): ImageContent {
            return ImageContent(image, detailLevel)
        }
    }
}
