# http4k GraphQL
This example has a single endpoint, served at the root, that returns some text. It only uses the `http4k-core` module, which is small and has zero dependencies apart from the Kotlin StdLib.

## Build/test locally

```shell script
./gradlew test distZip
unzip build/distributions/Example.zip
Example/bin/Example
```

then:
```shell script
curl -v http://localhost:8080/
```

## Build/run in Docker

```shell script
./build_and_run.sh
```

then:
```shell script
curl -v http://localhost:8080/
```

#### Simple
Once the app has started you can explore the example schema by opening Playground endpoint at: [http://localhost:5000/graphql/book]

You can use the following example queries to view:
```graphql
query {
  search(params: { ids: [1,2,3] }) {
    id
    name
  }
}
```
... or mutate:
```graphql
mutation {
  delete(params: { ids: [1] })
}
```

#### Contextual
Once the app has started you can explore the example schema by opening Playground endpoint at: [http://localhost:5000/graphql/user]

You can use the following example queries to view:
```graphql
query {
  search(params: { ids: [1,2,3] }) {
    id
    name
  }
}
```
... or mutate:
```graphql
mutation {
  delete(params: { ids: [1] })
}
```
