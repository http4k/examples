
## http4k Connect examples

These sample projects demonstrate how to use http4k-connect, a set of lightweight API connectors and Fake implementations for common third-party systems. http4k-connect allows your application to talk to external systems in a consistent, typesafe way, with minimal boilerplate.

### Connect Pattern
- **connect-pattern**: Code accompanying [the post](https://dentondav.id/posts/2021/02/smash-your-adapter-monolith) describing the basic Connect pattern and how it is structured. It only relies on [http4k](https://http4k.org) as a basic implementation platform.

### Using Connect APIs
- **using-connect-api-clients**: How to use the library API Clients (in this case AWS KMS) and Fake implementations which ship with http4k-connect.
- **fakes-in-official-aws-sdk**: How to use fake AWS API Clients with the official V2 AWS SDK in your tests.

### Extending Connect APIs  
- **extending-connect-api-clients**: Shows how to extend the library API Clients (in this case GitHub) with custom Action classes.
- **custom-api-client-and-fake**: Implementing your own custom http4k-connect compliant api-clients and a Fake HTTP system to connect to, along with examples of testing techniques. Also demonstrates the usage of the KSP compile-time code-generators in custom api-clients.
