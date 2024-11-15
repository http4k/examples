#!/bin/bash
set -e

echo "Building http4k for Quarkus..."
./gradlew clean build -Dquarkus.package.type=native -Dquarkus.native.container-build=true

echo "Adding app to Docker image..."
docker build . -t http4k-quarkus-example

echo "Running http4k on Quarkus..."
docker build . -t http4k-quarkus-example && docker run -p 8080:8080 http4k-quarkus-example
