package dev.langchain4j.data.message

import dev.langchain4j.data.message.AudioContent
import dev.langchain4j.data.message.ImageContent
import dev.langchain4j.data.message.PdfFileContent
import dev.langchain4j.data.message.VideoContent

/**
 * The type of content, e.g. text or image.
 * Maps to implementations of [Content].
 */
enum class ContentType(
    /**
     * Returns the class of the content type.
     *
     * @return The class of the content type.
     */
    val contentClass: Class<out Content>
) {
    /**
     * Text content.
     */
    TEXT(TextContent::class.java),

    /**
     * Image content.
     */
    IMAGE(ImageContent::class.java),

    /**
     * Audio content.
     */
    AUDIO(AudioContent::class.java),

    /**
     * Video content.
     */
    VIDEO(VideoContent::class.java),

    /**
     * PDF file content.
     */
    PDF(PdfFileContent::class.java)
}
