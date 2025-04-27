# http4k-pro Hot-Reload Example

This example demonstrates how to use the http4k-pro hot-reload functionality to enable automatic reloading of your http4k application during development without requiring manual server restarts.

## Features

- Automatic reloading of your http4k application when source files change
- Built-in development server that watches for code changes
- Simple API for making any http4k application hot-reloadable

## How It Works

The hot-reload functionality is provided by the `http4k-tools-hotreload` module, which is part of the http4k-pro package. 

This example consists of:

1. `MyApp.kt` - A standard http4k application that returns an HTML response
2. `main.kt` - The entry point that sets up hot-reloading using the `HotReloadServer`

## Running the Example

To run this example with hot-reloading enabled:

```bash
./gradlew :http4k-pro:hot-reload:run
```

After starting the server, navigate to http://localhost:8000 in your browser. 

Try making changes to the HTML content in `MyApp.kt` and save the file. The browser will automatically reload with your changes.

## Implementation Details

The key components that enable hot-reloading are:

1. Creating a class that implements `HotReloadable<HttpHandler>`:
```kotlin
class ReloadableHttpApp : HotReloadable<HttpHandler> {
    override fun create() = MyApp()
}
```

2. Using the `HotReloadServer` to start a server with this reloadable app:
```kotlin
HotReloadServer.http<ReloadableHttpApp>().start()
```

## Dependencies

This example requires the http4k-pro package, specifically the `http4k-tools-hotreload` module:

```kotlin
dependencies {
    implementation("org.http4k:http4k-core")
    testImplementation("org.http4k.pro:http4k-tools-hotreload")
}
```

## License

This example is licensed under the Apache License, Version 2.0.

The `http4k-tools-hotreload` module is licensed under the [http4k Commercial License](https://www.http4k.org/commercial-license/).
