package a2a

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.greaterThan
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.valueOrNull
import org.http4k.ai.a2a.client.testA2AJsonRpcClient
import org.http4k.ai.a2a.model.A2ARole.ROLE_USER
import org.http4k.ai.a2a.model.Message
import org.http4k.ai.a2a.model.MessageId
import org.http4k.ai.a2a.model.Part
import org.http4k.ai.a2a.model.ResponseStream
import org.http4k.ai.a2a.model.Task
import org.http4k.ai.a2a.model.TaskState.TASK_STATE_COMPLETED
import org.http4k.ai.a2a.model.TaskState.TASK_STATE_WORKING
import org.junit.jupiter.api.Test

class RecipeAgentTest {

    private val client = recipeAgent.testA2AJsonRpcClient()

    @Test
    fun `agent card is discoverable`() {
        assertThat(client.agentCard(), equalTo(Success(recipeAgentCard)))
    }

    @Test
    fun `agent returns streaming task updates`() {
        val response = client.messageStream(
            Message(MessageId.of("test-msg"), ROLE_USER, listOf(Part.Text("pasta")))
        ).valueOrNull()!! as ResponseStream

        val items = response.toList()
        assertThat(items.size, greaterThan(1))

        val first = items.first() as Task
        assertThat(first.status.state, equalTo(TASK_STATE_WORKING))

        val last = items.last() as Task
        assertThat(last.status.state, equalTo(TASK_STATE_COMPLETED))
    }
}
