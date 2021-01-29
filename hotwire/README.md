# http4k Hotwire Example

## Build/test locally
In your IDE run `HotWireKt` main then browse to [http://localhost:8080]

or...

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
