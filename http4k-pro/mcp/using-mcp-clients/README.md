# Using MCP Clients

This module demonstrates how to use the http4k MCP client to interact with MCP servers.

## Overview

The http4k-pro MCP client library provides a simple way to connect to and interact with MCP servers from your applications. This example shows the various capabilities available.

## Features Demonstrated

### Tool Calling
Examples of how to call tools exposed by MCP servers, including:
- Parameter passing
- Result handling
- Error management

### Resource Management
Working with resources provided by MCP servers:
- Listing available resources
- Accessing resource content
- Resource lifecycle management

### Prompt Handling
Interacting with server-provided prompts:
- Discovering available prompts
- Executing prompts with parameters
- Processing prompt responses

### Completion Management
Managing conversation completions:
- Initiating completions
- Streaming responses
- Handling completion metadata

### Sampling
Handling sampling requests from MCP servers:
- Registering sampling handlers
- Processing sampling requests
- Returning model responses

### Elicitation
Managing elicitation requests from servers:
- Handling user interaction requests
- Processing elicitation actions
- Returning user decisions

## Running the Example

The example demonstrates connecting to and interacting with various MCP servers.

## Code Structure

The [example.kt](./src/main/kotlin/using_mcp_client/example.kt) file contains comprehensive examples of:
- Client configuration and connection
- All major MCP operations (tools, resources, prompts, completions)
- Sampling and elicitation handlers
- Best practices for error handling
- Resource cleanup and connection management

## Integration

This client can be integrated into any http4k application to add MCP capabilities, enabling your application to interact with AI agents and other MCP-enabled services.