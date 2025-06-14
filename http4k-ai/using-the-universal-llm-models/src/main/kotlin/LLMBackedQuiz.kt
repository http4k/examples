import dev.forkhandles.result4k.orThrow
import org.http4k.ai.llm.chat.Chat
import org.http4k.ai.llm.chat.ChatRequest
import org.http4k.ai.llm.chat.ChatResponse
import org.http4k.ai.llm.model.Content
import org.http4k.ai.llm.model.Message
import org.http4k.ai.llm.model.ModelParams
import org.http4k.ai.model.ModelName

class LLMBackedQuiz(private val chat: Chat, modelName: ModelName) {

    private val params = ModelParams(modelName = modelName)

    fun query(question: String): String {

        val query = ChatRequest(Message.User(question), params = params)

        val response: ChatResponse = chat(query).orThrow(::error)

        return response
            .message
            .contents
            .filterIsInstance<Content.Text>()
            .joinToString("") { it.text }
    }
}


