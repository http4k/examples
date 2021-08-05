# http4k Hexagonal/DomainDrivenTest example
This example is an example of how to write Hexagonal http4k applications with DomainDrivenTests to test them at various levels of abstraction.

The use case for this example is that we have a marketplace buyer marking a sold item as dispatched. The system sends a notification with the submitted Tracking Number to the Buyer's phone.

The abstraction levels for the test examples are:

1. Domain level - running entirely in-memory.
1. API level - using the HTTP API - running entirely in-memory.
1. Deployed level - using the HTTP API - running against a running server (local or remote).
1. Browser level - using the WebDriver API - running against a running Webapp.

Note that we have not implemented the WebApp here so the tests are disabled.

## Build/test locally

```shell script
./gradlew test
```
