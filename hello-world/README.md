# http4k Hello World example application

## Build/test locally

```shell script
./gradlew test distZip
unzip build/distributions/Example.zip
Example/bin/Example
```

then:
```shell script
curl -v http://localhost:8080/hello
```

## Build/run in Docker

```shell script
./build_and_run.sh
```

then:
```shell script
curl -v http://localhost:8080/hello
```
