package building_servers.sse

import org.http4k.mcp.model.McpEntity
import org.http4k.mcp.protocol.ServerProtocolCapability
import org.http4k.mcp.protocol.ServerMetaData
import org.http4k.mcp.protocol.Version
import org.http4k.routing.mcpSse
import org.http4k.server.JettyLoom
import org.http4k.server.asServer
import building_servers.completions
import building_servers.prompts
import building_servers.resources
import building_servers.tools

/**
 * This example demonstrates how to create an MCP server using the standard SSE protocol.
 */
fun main() {
    val mcpServer = mcpSse(
        ServerMetaData(
            McpEntity.of("http4k mcp via SSE"), Version.of("0.1.0"),
            *ServerProtocolCapability.entries.toTypedArray()
        ),
        prompts(),
        resources(),
        tools(),
        completions()
    )

    mcpServer.asServer(JettyLoom(4001)).start()
}
