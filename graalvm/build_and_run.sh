#!/bin/bash

./gradlew clean test shadowJar

echo "Building http4k for GraalVM..."

docker build . -t http4k-graal-example

echo "Running http4k on GraalVM"
docker run -p 8080:8080 http4k-graal-example
