#!/bin/bash

echo "Building http4k for Quarkus..."
./gradlew clean test buildNative

echo "Adding app to Docker image..."
docker build . -t http4k-quarkus-example

echo "Running http4k on Quarkus..."
docker run -p 8080:8080 http4k-quarkus-example
