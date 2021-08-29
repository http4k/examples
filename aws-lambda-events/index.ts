import * as pulumi from "@pulumi/pulumi";
import * as aws from "@pulumi/aws";
import {RolePolicyAttachment} from "@pulumi/aws/iam";

const defaultRole = new aws.iam.Role("http4k-example-events-lambda-default-role", {
    assumeRolePolicy: `{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
`
});

new RolePolicyAttachment("http4k-example-events-lambda-default-role-policy",
    {
        role: defaultRole,
        policyArn: aws.iam.ManagedPolicies.AWSLambdaBasicExecutionRole
    });

const lambdaFunction = new aws.lambda.Function("http4k-example-events-lambda", {
    code: new pulumi.asset.FileArchive("build/distributions/http4k-aws-lambda-example-events-1.0-SNAPSHOT.zip"),
    handler: "org.http4k.example.EventFunction",
    role: defaultRole.arn,
    runtime: "java11",
    timeout: 15
});

const logGroupApi = new aws.cloudwatch.LogGroup("http4k-example-events-api-route", {
    name: "http4k-example",
});
