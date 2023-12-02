#!/bin/bash

./gradlew clean test shadowJar

echo "Building http4k for GraalVM..."

docker build --platform=linux/arm64/v8 --progress=plain . -t http4k-graal-example

echo "Running http4k on GraalVM..."
docker run --platform=linux/arm64/v8 -p 8080:8080 http4k-graal-example
