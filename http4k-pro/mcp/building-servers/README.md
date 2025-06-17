# MCP Building Servers

This module contains examples of building different types of MCP servers using http4k-pro.

## Examples

### [minimal](./src/main/kotlin/building_servers/minimal)
A simple, single-tool server example using SSE protocol. Perfect starting point for understanding MCP server basics.

### [http](./src/main/kotlin/building_servers/http)
Full-featured HTTP-based MCP server demonstrating REST-style communication.

### [websocket](./src/main/kotlin/building_servers/websocket)
MCP server using WebSockets for real-time bidirectional communication.

### [sse](./src/main/kotlin/building_servers/sse)
MCP server using Server-Sent Events for real-time updates from server to client.

### [jsonrpc](./src/main/kotlin/building_servers/jsonrpc)
MCP server implementing JSON-RPC protocol for structured communication.

### [stdio](./src/main/kotlin/building_servers/stdio)
Command-line interface MCP server that communicates via standard input/output.

### [serverless](./src/main/kotlin/building_servers/serverless)
AWS Lambda-compatible MCP function for serverless deployments.

### [security](./src/main/kotlin/building_servers/security)
Example of implementing security features in MCP servers.

## Running Examples

Each example can be run directly:

```bash
# Run the minimal server
./gradlew :http4k-pro:mcp:building-servers:run -Dexec.mainClass=building_servers.minimal.MinimalKt

# Run the HTTP server
./gradlew :http4k-pro:mcp:building-servers:run -Dexec.mainClass=building_servers.http.ExampleKt
```
