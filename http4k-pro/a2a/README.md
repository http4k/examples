# http4k A2A (Agent2Agent Protocol)

This example demonstrates how to build and test an A2A agent using http4k.

## What's included

- **RecipeAgent.kt** — A streaming A2A agent that responds to recipe queries with task status updates
- **ClientExample.kt** — A client that connects to the running agent and streams responses
- **RecipeAgentTest.kt** — In-memory tests using `testA2AJsonRpcClient()` — no server needed

## Running

Start the agent:
```bash
./gradlew :a2a:run
```

In another terminal, run the client:
```bash
./gradlew :a2a:run -PmainClass=a2a.ClientExampleKt
```

## Testing

```bash
./gradlew :a2a:test
```

Tests run fully in-memory — no network, no ports.
