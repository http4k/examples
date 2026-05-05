package a2a

import org.http4k.ai.a2a.model.A2ARole.ROLE_AGENT
import org.http4k.ai.a2a.model.AgentCapabilities
import org.http4k.ai.a2a.model.AgentCard
import org.http4k.ai.a2a.model.AgentSkill
import org.http4k.ai.a2a.model.ContextId
import org.http4k.ai.a2a.model.Message
import org.http4k.ai.a2a.model.MessageId
import org.http4k.ai.a2a.model.Part
import org.http4k.ai.a2a.model.ResponseStream
import org.http4k.ai.a2a.model.SkillId
import org.http4k.ai.a2a.model.Task
import org.http4k.ai.a2a.model.TaskId
import org.http4k.ai.a2a.model.TaskState.TASK_STATE_COMPLETED
import org.http4k.ai.a2a.model.TaskState.TASK_STATE_WORKING
import org.http4k.ai.a2a.model.TaskStatus
import org.http4k.ai.a2a.model.Version
import org.http4k.connect.model.MimeType
import org.http4k.routing.a2aJsonRpc
import org.http4k.server.Helidon
import org.http4k.server.asServer
import java.util.UUID

val recipeAgentCard = AgentCard(
    name = "Recipe Agent",
    version = Version.of("1.0.0"),
    description = "An agent that helps users find and explore recipes",
    capabilities = AgentCapabilities(streaming = true),
    defaultInputModes = listOf(MimeType.of("text/plain")),
    defaultOutputModes = listOf(MimeType.of("text/plain")),
    skills = listOf(
        AgentSkill(
            id = SkillId.of("find-recipe"),
            name = "Find Recipe",
            description = "Search for recipes by ingredients or cuisine",
            tags = listOf("cooking", "recipes", "search")
        ),
        AgentSkill(
            id = SkillId.of("nutrition"),
            name = "Nutrition Info",
            description = "Get nutritional breakdown for a recipe",
            tags = listOf("nutrition", "health")
        )
    )
)

val recipeAgent = a2aJsonRpc(recipeAgentCard, messageHandler = { request ->
    val query = request.message.parts.filterIsInstance<Part.Text>().joinToString(" ") { it.text }
    val taskId = TaskId.of(UUID.randomUUID().toString())
    val contextId = ContextId.of(UUID.randomUUID().toString())

    ResponseStream(
        sequenceOf(
            Task(
                id = taskId,
                status = TaskStatus(state = TASK_STATE_WORKING),
                contextId = contextId,
                history = listOf(request.message)
            ),
            Task(
                id = taskId,
                status = TaskStatus(
                    state = TASK_STATE_COMPLETED,
                    message = Message(
                        messageId = MessageId.random(),
                        role = ROLE_AGENT,
                        parts = listOf(Part.Text("Found recipes for: $query\n\n1. Pasta Carbonara\n2. Tomato Basil Soup\n3. Grilled Vegetables"))
                    )
                ),
                contextId = contextId
            )
        )
    )
})

fun main() {
    recipeAgent.asServer(Helidon(9000)).start()
    println("Recipe Agent running on http://localhost:9000")
    println("Agent Card at http://localhost:9000/.well-known/agent-card.json")
}
