@file:Suppress("unused")

package building_servers.serverless

import org.http4k.mcp.model.McpEntity
import org.http4k.mcp.protocol.ServerMetaData
import org.http4k.mcp.protocol.Version
import org.http4k.routing.mcpHttpNonStreaming
import org.http4k.serverless.ApiGatewayV2LambdaFunction
import org.http4k.serverless.AppLoader
import building_servers.completions
import building_servers.prompts
import building_servers.resources
import building_servers.tools
import org.http4k.mcp.server.security.NoMcpSecurity

/**
 * This example demonstrates how to create an MCP Lambda function using the non-streaming HTTP protocol.
 */
class McpLambdaFunction : ApiGatewayV2LambdaFunction(AppLoader {
    mcpHttpNonStreaming(
        ServerMetaData(McpEntity.of("http4k mcp over serverless"), Version.of("0.1.0")),
        NoMcpSecurity,
        prompts(),
        resources(),
        tools(),
        completions()
    )
})
