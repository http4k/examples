package dev.langchain4j.data.segment

import java.util.Objects
import java.util.stream.Collectors

/**
 * Defines the interface for transforming a [TextSegment].
 * Implementations can perform a variety of tasks such as transforming, filtering, enriching, etc.
 */
interface TextSegmentTransformer {
    /**
     * Transforms a provided segment.
     *
     * @param segment The segment to be transformed.
     * @return The transformed segment, or null if the segment should be filtered out.
     */
    fun transform(segment: TextSegment?): TextSegment?

    /**
     * Transforms all the provided segments.
     *
     * @param segments A list of segments to be transformed.
     * @return A list of transformed segments. The length of this list may be shorter or longer than the original list. Returns an empty list if all segments were filtered out.
     */
    fun transformAll(segments: List<TextSegment?>): List<TextSegment?> {
        return segments.stream()
            .map { segment: TextSegment? -> this.transform(segment) }
            .filter { obj: TextSegment? -> Objects.nonNull(obj) }
            .collect(Collectors.toList())
    }
}
