# IRC WebSockets
This example demonstrates how to build a simple WebSocket-powered IRC clone with http4k. The full application is 30 lines of code (when imports are excluded).

- http4k core `http4k-core`
- Undertow server module `http4k-server-jetty` including unit-testable Websockets
- http4k core `http4k-cloudnative` for configuration
- Shared behavioural contracts for Unit (offline) and Server (online) testing of Websockets. Ie. 1 contract, reusable across testing scopes.

## In action:

<img src="https://github.com/http4k/examples/raw/master/websockets/screenshot.png"/>

## Running it locally

Required environment variables:
```
CREDENTIALS=<user>:<password>     // for basic auth on the site
```

The credentials default to: **http4k**/**http4k**

Set the above environment variables and run the `IrcLauncher` class. The app will be available on [http://localhost:5000](http://localhost:5000). Use multiple browsers to see the connection/disconnection.
