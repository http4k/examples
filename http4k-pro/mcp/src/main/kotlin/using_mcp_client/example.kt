package using_mcp_client

import dev.forkhandles.result4k.valueOrNull
import org.http4k.client.JavaHttpClient
import org.http4k.connect.model.ToolName
import org.http4k.core.BodyMode.Stream
import org.http4k.core.Uri
import org.http4k.mcp.CompletionRequest
import org.http4k.mcp.PromptRequest
import org.http4k.mcp.ResourceRequest
import org.http4k.mcp.ToolRequest
import org.http4k.mcp.client.http.HttpStreamingMcpClient
import org.http4k.mcp.model.CompletionArgument
import org.http4k.mcp.model.McpEntity
import org.http4k.mcp.model.PromptName
import org.http4k.mcp.model.Reference
import org.http4k.mcp.protocol.ClientCapabilities
import org.http4k.mcp.protocol.Version

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

    mcpClient.stop()
}
