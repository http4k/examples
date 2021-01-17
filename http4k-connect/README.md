# http4k-connect integrations and fake dependencies

This project contains the following pieces which show how to use http4k-connect:

1. Apps which show how to use the http4k-connect AWS clients, one for S3 and the other for KMS. Run the `RunnableEnvironment` from your IDE to see them running with the FakeKMS and FakeS3 provided by http4k-connect. The apps run on port 8080 and 9090.
   
2. How to extend http4k-connect to build a lightweight adapter and a Fake for a new "ExpensesSystem". You need to write the following simple pieces to make this work:

a. `ExpensesAction` - An Action interface which defines the actions used by the ExpensesSystem.
b. `ExpensesSystem` - The http4k-connect interface that accepts the ExpensesActions. Note that this is annotated with `@Http4kConnectAdapter` which is used by the Kapt annotation processor. This interface is test-friendly (with or without mocks).
c. `GetMyExpenses` - The implementation of an `ExpensesAction` which retrieves expenses for a named person. Note that all of the Action and response classes are Kotlin Data classes so are test-friendly. Note that the action is annotated with `@Http4kConnectAction` which is used by the Kapt annotation processor to generate an IDE-friendly method `ExpensesSystem.getMyExpenses()`.
d. `ExpensesClient` - A client domain wrapper to wrap our `ExpensesSystem`.
e. `ExpensesSystem.Http` - HTTP implementation of the ExpensesSystem which wraps the underlying http4k HttpHandler.
f. `FakeExpensesSystem` - A fake implementation of our external system.
g. `build.gradle` - Note the additions of the Kapt plugin and the `org.http4k:http4k-connect-kapt-generator` which will generate the convenience methods for the Actions during the compile phase.

So after all of that, what do you get? Well, you get:

1. a consistent API-driven interface which returns a Result4k monad
2. a client interface which is extensible by others using the same mechanic 
3. a runnable, Chaos-enabled Fake implementation of the service, which also doubles as a standard HttpHandler, so can be swapped out to run tests edge-to-edge and completely in-memory.
