# http4k CloudNative configuration example
This example demonstrates how to configure http4k apps using the typesafe configuration facility in the `http4k-cloudnative` module. Configuring applications in this way conveys the following benefits:

- It's simple - configuration should not be Turing complete!
- Configuration parameters can be statically or dynamically defined.
- Uses http4k lenses for typesafety of standard types, and can be extended for your own types. Never misconfigure a timeout in milliseconds instead of seconds again!
- Configurable for Optionality (required, optional, defaulted, fallback) and Multiplicity (1..*).
- Overridable - 
- Application crashes on startup if misconfiguration detected.

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
