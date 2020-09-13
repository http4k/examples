#!/bin/bash

./gradlew clean test distZip

docker build . -t http4k-example

docker run -p 8080:8080 -e TIMEOUT=PT2S http4k-example
