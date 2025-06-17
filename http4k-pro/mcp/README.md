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

### [multi-server-example](./multi-server-example)
A real-world demonstration of a multi-server MCP system with three specialized servers:
- **Family Agent Server** - Personal assistant server for handling health insurance claims
- **Rainforest.com Server** - E-commerce server for purchasing items
- **Acme Health Insurance Server** - Insurance company server for processing claims

### [using-mcp-clients](./using-mcp-clients)
Examples demonstrating how to use the http4k MCP client to interact with MCP servers, including:
- Calling tools
- Using resources
- Working with prompts
- Managing completions

## Running Examples

Each example is a standalone application that demonstrates specific MCP functionality.

## License

These examples require an http4k-pro license for commercial use. See the [http4k Commercial License](https://www.http4k.org/commercial-license/) for details.
