package dev.langchain4j.rag.query.transformer

import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.data.segment.TextSegment.Companion.from
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.input.Prompt
import dev.langchain4j.model.input.PromptTemplate
import dev.langchain4j.rag.query.Query
import java.util.Objects
import java.util.stream.Collectors

/**
 * A [QueryTransformer] that leverages a [ChatModel] to condense a given [Query]
 * along with a chat memory (previous conversation history) into a concise [Query].
 * This is applicable only when a [ChatMemory] is in use.
 * Refer to [.DEFAULT_PROMPT_TEMPLATE] and implementation for more details.
 * <br></br>
 * <br></br>
 * Configurable parameters (optional):
 * <br></br>
 * - [.promptTemplate]: The prompt template used to instruct the LLM to compress the specified [Query].
 *
 * @see DefaultQueryTransformer
 *
 * @see ExpandingQueryTransformer
 */
class CompressingQueryTransformer @JvmOverloads constructor(
    chatModel: ChatModel?,
    promptTemplate: PromptTemplate? = DEFAULT_PROMPT_TEMPLATE
) :
    QueryTransformer {
    protected val promptTemplate: PromptTemplate?
    protected val chatModel: ChatModel

    init {
        this.chatModel = ValidationUtils.ensureNotNull(chatModel, "chatModel")
        this.promptTemplate = Utils.getOrDefault(promptTemplate, DEFAULT_PROMPT_TEMPLATE)
    }

    override fun transform(query: Query): Collection<Query> {
        val chatMemory = query.metadata()!!.chatMemory()
        if (chatMemory!!.isEmpty()) {
            // no need to compress if there are no previous messages
            return listOf(query)
        }

        val prompt = createPrompt(query, format(chatMemory))
        val compressedQueryText = chatModel.chat(prompt.text())
        val compressedQuery: Query = if (query.metadata() == null)
            Query.Companion.from(compressedQueryText)
        else
            Query.Companion.from(compressedQueryText, query.metadata())
        return listOf(compressedQuery)
    }

    protected fun format(chatMemory: List<ChatMessage?>): String {
        return chatMemory.stream()
            .map { message: ChatMessage? -> this.format(message) }
            .filter { obj: String? -> Objects.nonNull(obj) }
            .collect(Collectors.joining("\n"))
    }

    protected fun format(message: ChatMessage?): String? {
        if (message is UserMessage) {
            return "User: " + message.singleText()
        } else if (message is AiMessage) {
            if (message.hasToolExecutionRequests()) {
                return null
            }
            return "AI: " + message.text()
        } else {
            return null
        }
    }

    protected fun createPrompt(query: Query, chatMemory: String?): Prompt {
        val variables: MutableMap<String, Any?> = HashMap()
        variables["query"] = query.text()
        variables["chatMemory"] = chatMemory
        return promptTemplate!!.apply(variables)
    }

    class CompressingQueryTransformerBuilder internal constructor() {
        private var chatModel: ChatModel? = null
        private var promptTemplate: PromptTemplate? = null

        fun chatModel(chatModel: ChatModel?): CompressingQueryTransformerBuilder {
            this.chatModel = chatModel
            return this
        }

        fun promptTemplate(promptTemplate: PromptTemplate?): CompressingQueryTransformerBuilder {
            this.promptTemplate = promptTemplate
            return this
        }

        fun build(): CompressingQueryTransformer {
            return CompressingQueryTransformer(this.chatModel, this.promptTemplate)
        }
    }

    companion object {
        val DEFAULT_PROMPT_TEMPLATE: PromptTemplate = PromptTemplate.from(
            """
                    Read and understand the conversation between the User and the AI. Then, analyze the new query from the User. Identify all relevant details, terms, and context from both the conversation and the new query. Reformulate this query into a clear, concise, and self-contained format suitable for information retrieval.
                    
                    Conversation:
                    {{chatMemory}}
                    
                    User query: {{query}}
                    
                    It is very important that you provide only reformulated query and nothing else! Do not prepend a query with anything!
                    
                    """.trimIndent()
        )

        fun builder(): CompressingQueryTransformerBuilder {
            return CompressingQueryTransformerBuilder()
        }
    }
}
