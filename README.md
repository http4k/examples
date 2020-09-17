# http4k Examples

![.github/workflows/build.yaml](https://github.com/http4k/examples/workflows/.github/workflows/build.yaml/badge.svg)

These sample projects are designed to demonstrate how easy it is to use the various features of http4k in isolation. They are all entirely self-contained and can be run locally or using the included script to run them in a Docker container.

- **hello-world** : "Hello world" application with a single endpoint.
- **graalvm** :  "Hello world" application with a single endpoint, compiled to a native binary using GraalVM.
- **json-api** : A app which uses Jackson to automatically marshall Kotlin data classes into JSON objects.
- **oauth** : Using the http4k OAuth support, shows how to protect resources for use with an external auth provider such as Google or Auth0.
- **openapi** : Using the http4k contract routing module to build an API which is auto-documented in a generated OpenAPI3 specification.
- **quarkus** :  "Hello world" application with a single endpoint, compiled to a native binary using Quarkus.
- **typesafe-configuration** : Application configured using the typesafe configuration facility in the `http4k-cloudnative` module..
- **web-content** : Using the http4k templating system and serving static content.
- **aws-lambda**: Building and deploying `HttpHandler` as a serverless function.

