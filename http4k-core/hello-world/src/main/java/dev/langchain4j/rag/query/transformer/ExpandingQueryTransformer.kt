package dev.langchain4j.rag.query.transformer

import dev.langchain4j.data.segment.TextSegment.Companion.from
import dev.langchain4j.internal.Utils
import dev.langchain4j.internal.ValidationUtils
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.input.Prompt
import dev.langchain4j.model.input.PromptTemplate
import dev.langchain4j.rag.query.Query
import java.util.Arrays
import java.util.stream.Collectors

/**
 * A [QueryTransformer] that utilizes a [ChatModel] to expand a given [Query].
 * <br></br>
 * Refer to [.DEFAULT_PROMPT_TEMPLATE] and implementation for more details.
 * <br></br>
 * <br></br>
 * Configurable parameters (optional):
 * <br></br>
 * - [.promptTemplate]: The prompt template used to instruct the LLM to expand the provided [Query].
 * <br></br>
 * - [.n]: The number of [Query]s to generate. Default value is 3.
 *
 * @see DefaultQueryTransformer
 *
 * @see CompressingQueryTransformer
 */
class ExpandingQueryTransformer constructor(
    chatModel: ChatModel?,
    promptTemplate: PromptTemplate? = DEFAULT_PROMPT_TEMPLATE,
    n: Int? = DEFAULT_N
) :
    QueryTransformer {
    protected val chatModel: ChatModel
    protected val promptTemplate: PromptTemplate?
    protected val n: Int

    constructor(chatModel: ChatModel?, n: Int) : this(chatModel, DEFAULT_PROMPT_TEMPLATE, n)

    constructor(chatModel: ChatModel?, promptTemplate: PromptTemplate) : this(
        chatModel,
        ValidationUtils.ensureNotNull<PromptTemplate>(promptTemplate, "promptTemplate"),
        DEFAULT_N
    )

    init {
        this.chatModel = ValidationUtils.ensureNotNull(chatModel, "chatModel")
        this.promptTemplate = Utils.getOrDefault(promptTemplate, DEFAULT_PROMPT_TEMPLATE)
        this.n = ValidationUtils.ensureGreaterThanZero(Utils.getOrDefault(n, DEFAULT_N), "n")
    }

    override fun transform(query: Query): Collection<Query> {
        val prompt = createPrompt(query)
        val response = chatModel.chat(prompt.text())
        TODO()
//        val queries = parse(response)
//        return queries.stream()
//            .map<Query> { queryText: String? ->
//                if (query.metadata() == null)
//                    Query.Companion.from(queryText)
//                else
//                    Query.Companion.from(queryText, query.metadata())
//            }
//            .collect(Collectors.toList<Query>())
    }

    protected fun createPrompt(query: Query): Prompt {
        val variables: MutableMap<String, Any?> = HashMap()
        variables["query"] = query.text()
        variables["n"] = n
        return promptTemplate!!.apply(variables)
    }

    protected fun parse(queries: String): List<String> {
        return Arrays.stream(queries.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .filter { string: String? -> Utils.isNotNullOrBlank(string) }
            .collect(Collectors.toList())
    }

    class ExpandingQueryTransformerBuilder internal constructor() {
        private var chatModel: ChatModel? = null
        private var promptTemplate: PromptTemplate? = null
        private var n: Int? = null

        fun chatModel(chatModel: ChatModel?): ExpandingQueryTransformerBuilder {
            this.chatModel = chatModel
            return this
        }

        fun promptTemplate(promptTemplate: PromptTemplate?): ExpandingQueryTransformerBuilder {
            this.promptTemplate = promptTemplate
            return this
        }

        fun n(n: Int?): ExpandingQueryTransformerBuilder {
            this.n = n
            return this
        }

        fun build(): ExpandingQueryTransformer {
            return ExpandingQueryTransformer(this.chatModel, this.promptTemplate, this.n)
        }
    }

    companion object {
        val DEFAULT_PROMPT_TEMPLATE: PromptTemplate = PromptTemplate.from(
            """
                    Generate {{n}} different versions of a provided user query. Each version should be worded differently, using synonyms or alternative sentence structures, but they should all retain the original meaning. These versions will be used to retrieve relevant documents. It is very important to provide each query version on a separate line, without enumerations, hyphens, or any additional formatting!
                    User query: {{query}}
                    """.trimIndent()
        )
        const val DEFAULT_N: Int = 3

        fun builder(): ExpandingQueryTransformerBuilder {
            return ExpandingQueryTransformerBuilder()
        }
    }
}
