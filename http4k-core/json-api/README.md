# http4k JSON API example
This example shows how to create a JSON-format API with auto-marshalling of Kotlin Data classes to JSON using http4k lenses and the `http4k-format-jackson` module.

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
