# http4k Quarkus Hello World example
This example has a single endpoint, served at the root, that returns some text. It only uses the `http4k-core` module, which is small and has zero dependencies apart from the Kotlin StdLib. 

## Build/test local in quarkus devmode

```shell script
./gradlew test quarkusDev
```

then:
```shell script
curl -v http://localhost:8080/
```

## Build native binary locally

```shell script
./gradlew clean buildNative
```

## Build/run native binary then run in Docker

```shell script
./build_and_run.sh
```

then:
```shell script
curl -v http://localhost:8080/
```
