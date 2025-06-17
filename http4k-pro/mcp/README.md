# http4k MCP (Model Context Protocol)

This section contains examples demonstrating how to use http4k's MCP (Model Context Protocol) implementation, which is part of the http4k-pro offering.

## What is MCP?

The Model Context Protocol (MCP) is a standardized protocol for agent communication, providing a structured way for AI systems to interact with external tools, resources, and other AI systems. http4k-pro offers a complete implementation of this protocol.

## Modules

### [building-servers](./building-servers)
Examples of different MCP server implementations, including:
- **Minimal Server** - A simple, single-tool server example using SSE protocol
- **HTTP Server** - Full-featured HTTP-based MCP server  
- **WebSocket Server** - MCP server using WebSockets for communication
- **Server-Sent Events (SSE)** - MCP server using SSE for real-time updates
- **JSON-RPC Server** - MCP server with JSON-RPC protocol
- **stdio Server** - Command-line interface MCP server
- **Serverless** - AWS Lambda-compatible MCP function

### [configuring-claude](./configuring-claude)
Configuration examples for integrating MCP servers with Claude Desktop, including the necessary `claude_desktop_config.json` setup.

### [fully-featured-server](./fully-featured-server)
A real-world demonstration of a multi-MCP system with three specialized agents:
- **Family Agent** - Personal agent for handling health insurance claims
- **Rainforest.com** - E-commerce agent for purchasing items
- **Acme Health Insurance** - Insurance company agent for processing claims

### [using-mcp-clients](./using-mcp-clients)
Examples demonstrating how to use the http4k MCP client to interact with MCP servers, including:
- Calling tools
- Using resources
- Working with prompts
- Managing completions

## Running Examples

Most examples have a `main()` function that can be executed directly:

```bash
# Run the minimal server example
./gradlew :http4k-pro:mcp:building-servers:run -Dexec.mainClass=building_servers.minimal.MinimalKt
```

## License

These examples require an http4k-pro license for commercial use. See the [http4k Commercial License](https://www.http4k.org/commercial-license/) for details.
