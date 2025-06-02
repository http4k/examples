package dev.langchain4j.rag.content

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.rag.content.aggregator.ContentAggregator
import dev.langchain4j.rag.content.injector.ContentInjector
import dev.langchain4j.rag.content.retriever.ContentRetriever

/**
 * Represents content relevant to a user [Query] with the potential to enhance and ground the LLM's response.
 * <br></br>
 * Currently, it is limited to text content (i.e., [TextSegment]),
 * but future expansions may include support for other modalities (e.g., images, audio, video, etc.).
 *
 * @see ContentRetriever
 *
 * @see ContentAggregator
 *
 * @see ContentInjector
 */
interface Content {
    fun textSegment(): TextSegment

    fun metadata(): Map<ContentMetadata?, Any>

    companion object {
        fun from(text: String?): Content {
            return DefaultContent(text)
        }

        fun from(textSegment: TextSegment): Content {
            return DefaultContent(textSegment)
        }

        fun from(textSegment: TextSegment, metadata: Map<ContentMetadata?, Any>?): Content {
            return DefaultContent(textSegment, metadata)
        }
    }
}
