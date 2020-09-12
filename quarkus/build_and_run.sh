#!/bin/bash

echo "Building http4k for Quarkus..."

./gradlew clean test buildNative

echo "Running http4k on Quarkus..."

docker run -p 8080:8080 http4k-quarkus-example
