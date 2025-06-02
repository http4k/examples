package dev.langchain4j.rag.query.router

import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.internal.Utils
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.input.Prompt
import dev.langchain4j.model.input.PromptTemplate
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.query.Query
import dev.langchain4j.rag.query.router.LanguageModelQueryRouter.FallbackStrategy
import java.util.Arrays
import java.util.stream.Collectors

/**
 * A [QueryRouter] that utilizes a [ChatModel] to make a routing decision.
 * <br></br>
 * Each [ContentRetriever] provided in the constructor should be accompanied by a description which
 * should help the LLM to decide where to route a [Query].
 * <br></br>
 * Refer to [.DEFAULT_PROMPT_TEMPLATE] and implementation for more details.
 * <br></br>
 * <br></br>
 * Configurable parameters (optional):
 * <br></br>
 * - [.promptTemplate]: The prompt template used to ask the LLM for routing decisions.
 * <br></br>
 * - [.fallbackStrategy]: The strategy applied if the call to the LLM fails of if LLM does not return a valid response.
 * Please check [FallbackStrategy] for more details. Default value: [FallbackStrategy.DO_NOT_ROUTE]
 *
 * @see DefaultQueryRouter
 */
class LanguageModelQueryRouter @JvmOverloads constructor(
    chatModel: ChatModel?,
    retrieverToDescription: Map<ContentRetriever, String>?,
    promptTemplate: PromptTemplate? = DEFAULT_PROMPT_TEMPLATE,
    fallbackStrategy: FallbackStrategy? = FallbackStrategy.DO_NOT_ROUTE
) :
    QueryRouter {
    protected val chatModel: ChatModel
    protected val promptTemplate: PromptTemplate?
    protected val options: String
    protected val idToRetriever: Map<Int, ContentRetriever>
    protected val fallbackStrategy: FallbackStrategy

    init {
        this.chatModel = chatModel!!
        this.promptTemplate = Utils.getOrDefault(promptTemplate, DEFAULT_PROMPT_TEMPLATE)

        val idToRetriever: MutableMap<Int, ContentRetriever> = HashMap()
        val optionsBuilder = StringBuilder()
        var id = 1
        for ((key, value) in retrieverToDescription!!) {
            idToRetriever[id] =
                key!!

            if (id > 1) {
                optionsBuilder.append("\n")
            }
            optionsBuilder.append(id)
            optionsBuilder.append(": ")
            optionsBuilder.append(value!!)

            id++
        }
        this.idToRetriever = idToRetriever
        this.options = optionsBuilder.toString()
        this.fallbackStrategy = Utils.getOrDefault(fallbackStrategy, FallbackStrategy.DO_NOT_ROUTE)
    }

    override fun route(query: Query): Collection<ContentRetriever?> {
        val prompt = createPrompt(query)
        try {
            val response = chatModel.chat(prompt.text())
            TODO()
//            return parse(response)
        } catch (e: Exception) {
            return fallback(query, e)
        }
    }

    protected fun fallback(query: Query?, e: Exception?): Collection<ContentRetriever?> {
        return when (fallbackStrategy) {
            FallbackStrategy.DO_NOT_ROUTE -> {
                emptyList<ContentRetriever>()
            }

            FallbackStrategy.ROUTE_TO_ALL -> {
                ArrayList(idToRetriever.values)
            }

            else -> throw RuntimeException(e)
        }
    }

    protected fun createPrompt(query: Query): Prompt {
        val variables: MutableMap<String, Any?> = HashMap()
        variables["query"] = query.text()
        variables["options"] = options
        return promptTemplate!!.apply(variables)
    }

    protected fun parse(choices: String): Collection<ContentRetriever?> {
        return Arrays.stream(choices.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .map { obj: String -> obj.trim { it <= ' ' } }
            .map { s: String -> s.toInt() }
            .map { key: Int -> idToRetriever[key] }
            .collect(Collectors.toList())
    }

    /**
     * Strategy applied if the call to the LLM fails of if LLM does not return a valid response.
     * It could be because it was formatted improperly, or it is unclear where to route.
     */
    enum class FallbackStrategy {
        /**
         * In this case, the [Query] will not be routed to any [ContentRetriever],
         * thus skipping the RAG flow. No content will be appended to the original [UserMessage].
         */
        DO_NOT_ROUTE,

        /**
         * In this case, the [Query] will be routed to all [ContentRetriever]s.
         */
        ROUTE_TO_ALL,

        /**
         * In this case, an original exception will be re-thrown, and the RAG flow will fail.
         */
        FAIL
    }

    class LanguageModelQueryRouterBuilder internal constructor() {
        private var chatModel: ChatModel? = null
        private var retrieverToDescription: Map<ContentRetriever, String>? = null
        private var promptTemplate: PromptTemplate? = null
        private var fallbackStrategy: FallbackStrategy? = null

        fun chatModel(chatModel: ChatModel?): LanguageModelQueryRouterBuilder {
            this.chatModel = chatModel
            return this
        }

        fun retrieverToDescription(retrieverToDescription: Map<ContentRetriever, String>?): LanguageModelQueryRouterBuilder {
            this.retrieverToDescription = retrieverToDescription
            return this
        }

        fun promptTemplate(promptTemplate: PromptTemplate?): LanguageModelQueryRouterBuilder {
            this.promptTemplate = promptTemplate
            return this
        }

        fun fallbackStrategy(fallbackStrategy: FallbackStrategy?): LanguageModelQueryRouterBuilder {
            this.fallbackStrategy = fallbackStrategy
            return this
        }

        fun build(): LanguageModelQueryRouter {
            return LanguageModelQueryRouter(
                this.chatModel,
                this.retrieverToDescription,
                this.promptTemplate,
                this.fallbackStrategy
            )
        }
    }

    companion object {
        val DEFAULT_PROMPT_TEMPLATE: PromptTemplate = PromptTemplate.from(
            """
                    Based on the user query, determine the most suitable data source(s) to retrieve relevant information from the following options:
                    {{options}}
                    It is very important that your answer consists of either a single number or multiple numbers separated by commas and nothing else!
                    User query: {{query}}
                    """.trimIndent()
        )

        fun builder(): LanguageModelQueryRouterBuilder {
            return LanguageModelQueryRouterBuilder()
        }
    }
}
