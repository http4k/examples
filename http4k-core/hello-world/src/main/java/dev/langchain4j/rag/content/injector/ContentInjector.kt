package dev.langchain4j.rag.content.injector

import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.rag.content.Content

/**
 * Injects given [Content]s into a given [UserMessage].
 * <br></br>
 * The goal is to format and incorporate the [Content]s into the original [UserMessage]
 * enabling the LLM to utilize it for generating a grounded response.
 *
 * @see DefaultContentInjector
 */
interface ContentInjector {
    /**
     * Injects given [Content]s into a given [ChatMessage].
     *
     * @param contents    The list of [Content] to be injected.
     * @param chatMessage The [ChatMessage] into which the [Content]s are to be injected.
     * Currently, only [UserMessage] is supported.
     * @return The [ChatMessage] with the injected [Content]s.
     */
    fun inject(contents: List<Content?>, chatMessage: ChatMessage): ChatMessage
}
