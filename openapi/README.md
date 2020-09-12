# http4k OpenAPI example
This example shows how to create an API which exposes an OpenAPI3 specification document, driven by the code. It uses the `http4k-format-jackson` library and http4k lenses from the core module to provide automatic marshalling of JSON from Kotlin Data classes. 

Documentation for each endpoint is compile-safe, meaning that the OpenAPI3 spec served is always up to date, including JSON schema models generated from the code.

The best way to view this app is to use an OpenAPI UI. There is one provided for convenience on the http4k site. Start the app and browse to [here](https://http4k.org/openapi3/?url=http%3A%2F%2Flocalhost%3A8080%2F). 

## Build/test locally

```shell script
./gradlew test distZip
unzip build/distributions/Example.zip
Example/bin/Example
```

then:
```shell script
curl -v http://localhost:8080/
```

## Build/run in Docker

```shell script
./build_and_run.sh
```

then:
```shell script
curl -v http://localhost:8080/
```
