#!/bin/bash

set -e

./gradlew clean shadowJar

docker run -v $(pwd):/source http4k/amazoncorretto-lambda-runtime:latest build/libs/http4k-lambda.jar build/distributions/http4k-lambda.zip

pulumi up -y --stack dev

curl $(pulumi stack --stack dev output publishedUrl)
