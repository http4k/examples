## http4k Core examples

These sample projects are designed to demonstrate how easy it is to use the various features of http4k in isolation.

- **hello-world** : "Hello world" application with a single endpoint.

- **aws-lambda-custom-runtime**: Building and deploying `HttpHandler` as a Serverless function with the lightweight http4k custom runtime (on Graviton).
- **aws-lambda-http**: Building and deploying `HttpHandler` as a Serverless function.
- **aws-lambda-events**: Building and deploying an event-based `FnHandler` as a Serverless function.
- **graalvm** :  "Hello world" application with a single endpoint, compiled to a native binary using GraalVM.
- **graphql** : Using the http4k graphql routing module to build an API which exposes a data graph backed by the GraphQL-Java library.
- **hexagonal**: Writing Hexagonal http4k applications with DomainDrivenTests.
- **hexagonal-arrow**: Writing Hexagonal http4k applications with Functional Error Handling via Arrow with DomainDrivenTests.
- **hotwire** : Serve a [Hotwire](https://hotwire.dev/) application via http4k (with Websocket support)!
- **http4k-connect** : A app which utilises the `http4k-connect`  libraries, which provide both a framework for http4k client connectors to external systems and Fake versions of those systems.
- **json-api** : A app which uses Jackson to automatically marshall Kotlin data classes into JSON objects.
- **oauth** : Using the http4k OAuth support, shows how to protect resources for use with an external auth provider such as Google or Auth0.
- **openapi** : Using the http4k contract routing module to build an API which is auto-documented in a generated OpenAPI3 specification.
- **quarkus** :  "Hello world" application with a single endpoint, compiled to a native binary using Quarkus.
- **react-app** : Combines a React front end with an http4k backend.
- **typesafe-configuration** : Application configured using the typesafe configuration facility in the `http4k-cloudnative` module..
- **web-content** : Using the http4k templating system and serving static content.
- **bearer-auth** : Use nimbus-jose-jwt to secure an API with bearer authentication 
