package dev.langchain4j.rag

import dev.langchain4j.data.message.ChatMessage

/**
 * Augments the provided [ChatMessage] with retrieved [Content]s.
 * <br></br>
 * This serves as an entry point into the RAG flow in LangChain4j.
 * <br></br>
 * You are free to use the default implementation ([DefaultRetrievalAugmentor]) or to implement a custom one.
 *
 * @see DefaultRetrievalAugmentor
 */
interface RetrievalAugmentor {
    /**
     * Augments the [ChatMessage] provided in the [AugmentationRequest] with retrieved [Content]s.
     *
     * @param augmentationRequest The `AugmentationRequest` containing the `ChatMessage` to augment.
     * @return The [AugmentationResult] containing the augmented `ChatMessage`.
     */
    fun augment(augmentationRequest: AugmentationRequest): AugmentationResult
}
