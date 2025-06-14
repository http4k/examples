import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.startsWith
import org.http4k.ai.llm.chat.AnthropicAI
import org.http4k.ai.llm.chat.Chat
import org.http4k.ai.llm.chat.OpenAI
import org.http4k.ai.model.ApiKey
import org.http4k.ai.model.ModelName
import org.http4k.connect.anthropic.AnthropicModels.Claude_Sonnet_3_7
import org.http4k.connect.anthropic.FakeAnthropicAI
import org.http4k.connect.openai.FakeOpenAI
import org.http4k.connect.openai.OpenAIModels.GPT4
import org.junit.jupiter.api.Test


interface LLMBackedQuizContract {

    val chat: Chat
    val model: ModelName

    @Test
    fun `can query the llm`() {
        val response = LLMBackedQuiz(chat, model)
            .query("What is the capital of France?")

        // by default, the fake returns a response that
        // uses the LorumIpsum generator
        assertThat(response, startsWith("Lorem ipsum dolor"))
    }
}

class AnthropicQuizTest : LLMBackedQuizContract {
    override val chat = Chat.AnthropicAI(ApiKey.of("fake"), FakeAnthropicAI())
    override val model = Claude_Sonnet_3_7
}

// currently this won't run because of a classpath clash (being resolved in next release)
class OpenAIQuizTest : LLMBackedQuizContract {
    override val chat = Chat.OpenAI(ApiKey.of("fake"), FakeOpenAI())
    override val model = GPT4
}
