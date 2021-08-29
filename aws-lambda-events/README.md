### AWS Lambda (Events) + http4k example

This example shows how to build, deploy, and invoke an Event-based `FnHandler` as AWS Lambda.

## Pre-requisites

* Working AWS account
* Pulumi [installed](https://www.pulumi.com/docs/get-started/install/)
* Pulumi javascript dependencies installed via `npm install`
* A [user](https://aws.amazon.com/iam/) with permissions to manage resources
* User credentials configured in a `http4k-lambda-demo` [CLI profile](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-profiles.html): 

*~/.aws/config*:

```
[profile http4k-lambda-demo]
region = eu-west-2
output = json
```
*~/.aws/credentials*:

```
[http4k-lambda-demo]
aws_access_key_id = <your key>
aws_secret_access_key = <your secret>
```

## Running it

```bash
./gradlew buildZip
pulumi up --stack dev
```

To test, go to the AWS console and invoke the lambda manually using the SQS event template.

## Cleaning up

```bash
pulumi destroy --stack dev
```
