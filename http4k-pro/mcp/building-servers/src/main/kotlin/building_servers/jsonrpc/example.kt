package building_servers.jsonrpc

import org.http4k.client.JavaHttpClient
import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.filter.debug
import org.http4k.lens.contentType
import org.http4k.ai.mcp.model.McpEntity
import org.http4k.ai.mcp.protocol.ServerMetaData
import org.http4k.ai.mcp.protocol.Version
import org.http4k.routing.mcpJsonRpc
import org.http4k.server.JettyLoom
import org.http4k.server.asServer
import building_servers.completions
import building_servers.prompts
import building_servers.resources
import building_servers.tools
import org.http4k.ai.mcp.server.security.NoMcpSecurity

/**
 * This example demonstrates how to create an MCP server using the JSONRPC protocol.
 */
fun main() {
    val mcpServer = mcpJsonRpc(
        ServerMetaData(McpEntity.of("http4k mcp via jsonrpc"), Version.of("0.1.0")),
        NoMcpSecurity,
        prompts(),
        resources(),
        tools(),
        completions()
    )

    mcpServer.debug().asServer(JettyLoom(3001)).start()

    // you can use straight HTTP to interact with the server
    JavaHttpClient().debug()(
        Request(POST, "http://localhost:3001/jsonrpc")
            .contentType(APPLICATION_JSON)
            .body("""{"jsonrpc":"2.0","method":"tools/list","params":{"_meta":{}},"id":1}""")
    )
}
