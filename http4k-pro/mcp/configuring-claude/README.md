# Configuring Claude Desktop

This module contains configuration examples for integrating MCP servers with Claude Desktop.

## Files

### [claude_desktop_config.json](./claude_desktop_config.json)
Example configuration file for Claude Desktop showing how to register MCP servers for use with Claude.

This configuration file should be placed in the appropriate location for your platform:
- **macOS**: `~/Library/Application Support/Claude/claude_desktop_config.json`
- **Windows**: `%APPDATA%/Claude/claude_desktop_config.json`

## Configuration Format

The configuration file defines MCP servers that Claude Desktop can connect to:

```json
{
  "mcpServers": {
    "server-name": {
      "command": "path/to/server",
      "args": ["arg1", "arg2"],
      "env": {
        "ENV_VAR": "value"
      }
    }
  }
}
```

## Usage

1. Copy the example configuration file to your Claude Desktop configuration directory
2. Modify the server paths and arguments to match your MCP server setup
3. Restart Claude Desktop to load the new configuration
4. Your MCP servers will be available for use in Claude conversations