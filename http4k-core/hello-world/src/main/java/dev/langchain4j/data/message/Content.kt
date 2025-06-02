package dev.langchain4j.data.message

/**
 * Abstract base interface for message content.
 *
 * @see TextContent
 *
 * @see ImageContent
 *
 * @see AudioContent
 *
 * @see VideoContent
 *
 * @see PdfFileContent
 */
interface Content {
    /**
     * Returns the type of content.
     *
     *
     * Can be used to cast the content to the correct type.
     *
     * @return The type of content.
     */
    fun type(): ContentType
}
