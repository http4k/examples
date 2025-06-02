package dev.langchain4j.spi.json

import dev.langchain4j.internal.Json

/**
 * A factory for creating [Json.JsonCodec] instances through SPI.
 */
interface JsonCodecFactory {
    /**
     * Create a new [Json.JsonCodec].
     * @return the new [Json.JsonCodec].
     */
    fun create(): Json.JsonCodec?
}
