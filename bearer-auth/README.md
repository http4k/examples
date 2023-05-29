# http4k Bearer Auth Example
This example shows how to build an API protected with bearer authorization.
The access tokens in this example are JWTs, but any lookup can be used.

## Build/test locally

```shell script
./gradlew test run
```

To generate an access token:
```shell script
$ curl http://localhost:8080/token/<user_id>
```

To verify the access token:
```shell script
curl http://localhost:8080/hello --header "Authorization: Bearer <jwt>"
```

