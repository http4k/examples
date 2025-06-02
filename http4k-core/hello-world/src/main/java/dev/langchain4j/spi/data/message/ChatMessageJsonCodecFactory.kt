package dev.langchain4j.spi.data.message

import dev.langchain4j.data.message.ChatMessageJsonCodec

/**
 * A factory for creating [ChatMessageJsonCodec] objects.
 * Used for SPI.
 */
interface ChatMessageJsonCodecFactory {
    /**
     * Creates a new [ChatMessageJsonCodec] object.
     * @return the new [ChatMessageJsonCodec] object.
     */
    fun create(): ChatMessageJsonCodec?
}
