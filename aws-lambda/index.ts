import * as pulumi from "@pulumi/pulumi";
import * as aws from "@pulumi/aws";
import {RolePolicyAttachment} from "@pulumi/aws/iam";

const defaultRole = new aws.iam.Role("http4k-example-lambda-default-role", {
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

new RolePolicyAttachment("http4k-example-lambda-default-role-policy",
    {
        role: defaultRole,
        policyArn: aws.iam.ManagedPolicies.AWSLambdaBasicExecutionRole
    });

const lambdaFunction = new aws.lambda.Function("http4k-example-lambda", {
    code: new pulumi.asset.FileArchive("build/distributions/http4k-aws-lambda-example-1.0-SNAPSHOT.zip"),
    handler: "org.http4k.example.MyHttp4kFunction",
    role: defaultRole.arn,
    runtime: "java11",
    timeout: 15
});

const logGroupApi = new aws.cloudwatch.LogGroup("http4k-example-api-route", {
    name: "http4k-example",
});

const apiGatewayPermission = new aws.lambda.Permission("http4k-example-lambda-gateway-permission", {
    action: "lambda:InvokeFunction",
    "function": lambdaFunction.name,
    principal: "apigateway.amazonaws.com"
});

const api = new aws.apigatewayv2.Api("http4k-example-api", {
    protocolType: "HTTP"
});

const apiDefaultStage = new aws.apigatewayv2.Stage("default", {
    apiId: api.id,
    autoDeploy: true,
    name: "$default",
    accessLogSettings: {
        destinationArn: logGroupApi.arn,
        format: `{"requestId": "$context.requestId", "requestTime": "$context.requestTime", "httpMethod": "$context.httpMethod", "httpPath": "$context.path", "status": "$context.status", "integrationError": "$context.integrationErrorMessage"}`
    }
})

const lambdaIntegration = new aws.apigatewayv2.Integration("http4k-example-api-lambda-integration", {
    apiId: api.id,
    integrationType: "AWS_PROXY",
    integrationUri: lambdaFunction.arn,
    payloadFormatVersion: "1.0"
});

const apiDefaultRole = new aws.apigatewayv2.Route("http4k-example-api-route", {
    apiId: api.id,
    routeKey: `$default`,
    target: pulumi.interpolate `integrations/${lambdaIntegration.id}`
});

export const publishedUrl = apiDefaultStage.invokeUrl;
