#!/bin/bash

echo "Building http4k for GraalVM..."

./gradlew clean test nativeCompile

./build/native/nativeCompile/helloworld
