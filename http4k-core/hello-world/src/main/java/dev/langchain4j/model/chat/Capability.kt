package dev.langchain4j.model.chat

import dev.langchain4j.model.chat.request.ResponseFormat

/**
 * Represents a capability of a [ChatModel] or [StreamingChatModel].
 * This is required for the low-level [ChatModel] or [StreamingChatModel] API
 * to communicate to the high-level APIs (like AI Service) what capabilities are supported and can be utilized.
 */
enum class Capability {
    /**
     * Indicates whether [ChatModel] or [StreamingChatModel]
     * supports responding in JSON format according to the specified JSON schema.
     *
     * @see ResponseFormat
     *
     * @see JsonSchema
     */
    RESPONSE_FORMAT_JSON_SCHEMA
}
