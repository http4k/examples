# http4k GraalVM Hello World example
This example has a single endpoint, served at the root, that returns some text. It only uses the `http4k-core` module, which is small and has zero dependencies apart from the Kotlin StdLib.

GraalVM is a natural fit for http4k applications because of the lack of reflection/magic used throughout the library. This allows http4k to support native compilation with none of the detailed tweaking or complications that afflict other platforms.

## Build/test locally

```shell script
./gradlew test shadowJar
java -jar build/libs/Example-all.jar
```

then:
```shell script
curl -v http://localhost:8080/
```

## Build into a native binary and run in Docker

```shell script
./build_and_run.sh
```

then:
```shell script
curl -v http://localhost:8080/
```
