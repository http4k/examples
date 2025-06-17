package using_mcp_client

import dev.forkhandles.result4k.valueOrNull
import org.http4k.ai.mcp.CompletionRequest
import org.http4k.ai.mcp.ElicitationResponse
import org.http4k.ai.mcp.PromptRequest
import org.http4k.ai.mcp.ResourceRequest
import org.http4k.ai.mcp.SamplingResponse
import org.http4k.ai.mcp.ToolRequest
import org.http4k.ai.mcp.client.http.HttpStreamingMcpClient
import org.http4k.ai.mcp.model.CompletionArgument
import org.http4k.ai.mcp.model.Content.Text
import org.http4k.ai.mcp.model.ElicitationAction.accept
import org.http4k.ai.mcp.model.McpEntity
import org.http4k.ai.mcp.model.PromptName
import org.http4k.ai.mcp.model.Reference
import org.http4k.ai.mcp.protocol.ClientCapabilities
import org.http4k.ai.mcp.protocol.Version
import org.http4k.ai.mcp.util.McpJson
import org.http4k.ai.model.ModelName
import org.http4k.ai.model.Role
import org.http4k.ai.model.ToolName
import org.http4k.client.JavaHttpClient
import org.http4k.core.BodyMode.Stream
import org.http4k.core.Uri

fun main() {
    val mcpClient = HttpStreamingMcpClient(
        McpEntity.of("foobar"), Version.of("1.0.0"),
        Uri.of("http://localhost:3001/mcp"),
        JavaHttpClient(responseBodyMode = Stream),
        ClientCapabilities()
    )

    println(mcpClient.start())

    println(mcpClient.prompts().list())
    println(mcpClient.prompts().get(PromptName.of("prompt2"), PromptRequest(mapOf("a1" to "foo"))))

    println(mcpClient.resources().list())
    println(mcpClient.resources().read(ResourceRequest(Uri.of("https://www.http4k.org"))))

    println(
        mcpClient.completions()
            .complete(Reference.Prompt("prompt2"), CompletionRequest(CompletionArgument("foo", "bar")))
    )

    println(mcpClient.tools().list().valueOrNull())
    println(mcpClient.tools().call(ToolName.of("weather"), ToolRequest(mapOf("city" to "london"))))


    mcpClient.sampling().onSampled {
        println(">>> Sampled: $it")
        sequenceOf(SamplingResponse(ModelName.of("gpt-4"), Role.Assistant, Text("Sampled: $it")))
    }

    mcpClient.elicitations().onElicitation {
        println(">>> Elicitation: $it")
        val response = McpJson.obj()
        ElicitationResponse(accept, response)
    }



    mcpClient.stop()
}
