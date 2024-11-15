### AWS Lambda with custom http4k runtime + http4k example

This example shows how to build, deploy, and invoke an http4k `HttpHandler` as AWS Lambda with a custom http4k runtime 
running on AMD64 architecture.

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
./gradlew clean shadowJar

docker run -v $(pwd):/source http4k/amazoncorretto-lambda-runtime-arm64:latest build/libs/http4k-lambda.jar build/distributions/http4k-lambda.zip

pulumi up -y --stack dev
```

The deployed URL will be printed at the end of the run. You can test the deployed lambda with: 
```bash
curl $(pulumi stack --stack dev output publishedUrl) 
```

## Cleaning up

```bash
pulumi destroy --stack dev
```
