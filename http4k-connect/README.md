
## http4k Connect examples

These sample projects are designed to demonstrate how easy it is to use the various features of http4k in isolation.

- **connect-pattern** : Code accompanying [the post](https://dentondav.id/posts/2021/02/smash-your-adapter-monolith) describing the basic Connect pattern and how it is structured. It only relies on [http4k](https://http4k.org) as a basic implementation platform.
- **extending-connect-api-clients** : Shows how to extend the library API Clients (in this case GitHub) with custom Action classes.
- **using-connect-api-clients** : How to use the library API Clients (in this case AWS KMS) and Fake which ship with http4k-connect.
- **custom-api-clients-and-fake** : Implementing your own custom http4k-connect compliant api-clients and a Fake HTTP system to connect to, along with examples of testing techniques. Also demonstrates the usage of the Kapt compile-time code-generators in a custom api-clients.
- **fakes-in-official-aws-sdk**: How to use fake AWS API Clients with the official V2 AWS SDK in your tests.
