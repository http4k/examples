package building_servers.websocket

import org.http4k.filter.debug
import org.http4k.mcp.model.McpEntity
import org.http4k.mcp.protocol.ServerProtocolCapability
import org.http4k.mcp.protocol.ServerMetaData
import org.http4k.mcp.protocol.Version
import org.http4k.routing.mcpWebsocket
import org.http4k.server.JettyLoom
import org.http4k.server.asServer
import building_servers.completions
import building_servers.prompts
import building_servers.resources
import building_servers.tools
import org.http4k.mcp.server.security.NoMcpSecurity

/**
 * This example demonstrates how to create an MCP server using the standard WS protocol.
 */
fun main() {
    val mcpServer = mcpWebsocket(
        ServerMetaData(
            McpEntity.of("http4k mcp via WS"), Version.of("0.1.0"),
            *ServerProtocolCapability.entries.toTypedArray()
        ),
        NoMcpSecurity,
        prompts(),
        resources(),
        tools(),
        completions()
    )

    mcpServer.debug().asServer(JettyLoom(5001)).start()
}
