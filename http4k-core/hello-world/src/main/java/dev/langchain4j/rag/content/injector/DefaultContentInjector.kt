package dev.langchain4j.rag.content.injector

import dev.langchain4j.data.document.Metadata
import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.model.input.Prompt
import dev.langchain4j.model.input.PromptTemplate
import dev.langchain4j.rag.content.Content
import java.util.stream.Collectors

/**
 * Default implementation of [ContentInjector] intended to be suitable for the majority of use cases.
 * <br></br>
 * <br></br>
 * It's important to note that while efforts will be made to avoid breaking changes,
 * the default behavior of this class may be updated in the future if it's found
 * that the current behavior does not adequately serve the majority of use cases.
 * Such changes would be made to benefit both current and future users.
 * <br></br>
 * <br></br>
 * This implementation appends all given [Content]s to the end of the given [UserMessage]
 * in their order of iteration.
 * Refer to [.DEFAULT_PROMPT_TEMPLATE] and implementation for more details.
 * <br></br>
 * <br></br>
 * Configurable parameters (optional):
 * <br></br>
 * - [.promptTemplate]: The prompt template that defines how the original `userMessage`
 * and `contents` are combined into the resulting [UserMessage].
 * The text of the template should contain the `{{userMessage}}` and `{{contents}}` variables.
 * <br></br>
 * - [.metadataKeysToInclude]: A list of [Metadata] keys that should be included
 * with each [Content.textSegment].
 */
class DefaultContentInjector constructor(
    promptTemplate: PromptTemplate? = DEFAULT_PROMPT_TEMPLATE,
    metadataKeysToInclude: List<String>? = null
) :
    ContentInjector {
    private val promptTemplate: PromptTemplate?
    private val metadataKeysToInclude: List<String>

    constructor(metadataKeysToInclude: List<String>) : this(
        DEFAULT_PROMPT_TEMPLATE,
        ValidationUtils.ensureNotEmpty<List<String>>(metadataKeysToInclude, "metadataKeysToInclude")
    )

    constructor(promptTemplate: PromptTemplate) : this(
        ValidationUtils.ensureNotNull<PromptTemplate>(
            promptTemplate,
            "promptTemplate"
        ), null
    )

    init {
        this.promptTemplate = Utils.getOrDefault(promptTemplate, DEFAULT_PROMPT_TEMPLATE)
        this.metadataKeysToInclude = Utils.copy(metadataKeysToInclude)
    }

    override fun inject(contents: List<Content?>, chatMessage: ChatMessage): ChatMessage {
        if (contents.isEmpty()) {
            return chatMessage
        }

        return TODO()
//        val prompt = createPrompt(chatMessage, contents)
//        if (chatMessage is UserMessage && Utils.isNotNullOrBlank(chatMessage.name())) {
//            return prompt.toUserMessage(chatMessage.name())
//        }
//
//        return prompt.toUserMessage()
    }

    protected fun createPrompt(chatMessage: ChatMessage, contents: List<Content>): Prompt {
        val variables: MutableMap<String, Any?> = HashMap()
        variables["userMessage"] = (chatMessage as UserMessage).singleText()
        variables["contents"] = format(contents)
        return promptTemplate!!.apply(variables)
    }

    protected fun format(contents: List<Content>): String {
        return contents.stream().map { content: Content -> this.format(content) }.collect(Collectors.joining("\n\n"))
    }

    protected fun format(content: Content): String {
        val segment = content.textSegment()

        if (metadataKeysToInclude.isEmpty()) {
            return segment!!.text()
        }

        val segmentContent = segment!!.text()
        val segmentMetadata = format(segment.metadata())

        return format(segmentContent, segmentMetadata)
    }

    protected fun format(metadata: Metadata): String {
        val formattedMetadata = StringBuilder()
        for (metadataKey in metadataKeysToInclude) {
            val metadataValue = metadata.getString(metadataKey)
            if (metadataValue != null) {
                if (!formattedMetadata.isEmpty()) {
                    formattedMetadata.append("\n")
                }
                formattedMetadata.append(metadataKey).append(": ").append(metadataValue)
            }
        }
        return formattedMetadata.toString()
    }

    protected fun format(segmentContent: String, segmentMetadata: String): String {
        return if (segmentMetadata.isEmpty())
            segmentContent
        else String.format("content: %s\n%s", segmentContent, segmentMetadata)
    }

    class DefaultContentInjectorBuilder internal constructor() {
        private var promptTemplate: PromptTemplate? = null
        private var metadataKeysToInclude: List<String>? = null

        fun promptTemplate(promptTemplate: PromptTemplate?): DefaultContentInjectorBuilder {
            this.promptTemplate = promptTemplate
            return this
        }

        fun metadataKeysToInclude(metadataKeysToInclude: List<String>?): DefaultContentInjectorBuilder {
            this.metadataKeysToInclude = metadataKeysToInclude
            return this
        }

        fun build(): DefaultContentInjector {
            return DefaultContentInjector(this.promptTemplate, this.metadataKeysToInclude)
        }
    }

    companion object {
        val DEFAULT_PROMPT_TEMPLATE: PromptTemplate = PromptTemplate.from(
            """
                    {{userMessage}}

                    Answer using the following information:
                    {{contents}}
                    """.trimIndent()
        )

        fun builder(): DefaultContentInjectorBuilder {
            return DefaultContentInjectorBuilder()
        }
    }
}
