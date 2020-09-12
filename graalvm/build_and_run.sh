#!/bin/bash

./gradlew clean test shadowJar

docker build . -t http4k-example

docker run -p 8080:8080 http4k-example
