# http4k Hello World example
This example has a single endpoint, served at the root, that returns some text. It only uses the `http4k-core` module, which is small and has zero dependencies apart from the Kotlin StdLib.

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
