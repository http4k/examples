package building_servers.sse

import org.http4k.ai.mcp.model.McpEntity
import org.http4k.ai.mcp.protocol.ServerProtocolCapability
import org.http4k.ai.mcp.protocol.ServerMetaData
import org.http4k.ai.mcp.protocol.Version
import org.http4k.routing.mcpSse
import org.http4k.server.JettyLoom
import org.http4k.server.asServer
import building_servers.completions
import building_servers.prompts
import building_servers.resources
import building_servers.tools
import org.http4k.ai.mcp.server.security.NoMcpSecurity

/**
 * This example demonstrates how to create an MCP server using the standard SSE protocol.
 */
fun main() {
    val mcpServer = mcpSse(
        ServerMetaData(
            McpEntity.of("http4k mcp via SSE"), Version.of("0.1.0"),
            *ServerProtocolCapability.entries.toTypedArray()
        ),
        NoMcpSecurity,
        prompts(),
        resources(),
        tools(),
        completions()
    )

    mcpServer.asServer(JettyLoom(4001)).start()
}
