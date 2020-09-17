### AWS Lambda + http4k example

This example shows how to build, deploy, and invoke an http4k `HttpHandler` as AWS Lambda.

## Pre-requisites

* Working AWS account
* AWS CLI v2 [installed](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html)
* An [user](https://aws.amazon.com/iam/) with the permissions specified in `./scripts/user-permissions.json`
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

Run `./scripts/run_demo.sh`