import com.pulumi.Pulumi
import com.pulumi.asset.FileArchive
import com.pulumi.aws.iam.Role
import com.pulumi.aws.iam.RoleArgs
import com.pulumi.aws.iam.RolePolicyAttachment
import com.pulumi.aws.iam.RolePolicyAttachmentArgs
import com.pulumi.aws.lambda.Function
import com.pulumi.aws.lambda.FunctionArgs
import com.pulumi.aws.lambda.FunctionUrl
import com.pulumi.aws.lambda.FunctionUrlArgs

fun main() {
    Pulumi.run { context ->
        val lambdaRole = Role(
            "lambdaRole", RoleArgs
                .builder()
                .assumeRolePolicy(
                    """
                {
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
                """.trimIndent()
                ).build()
        )

        RolePolicyAttachment(
            "lambdaRoleAttachment", RolePolicyAttachmentArgs
                .builder()
                .role(lambdaRole.name())
                .policyArn("arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole")
                .build()
        )

        val function = Function(
            "hello-http4k", FunctionArgs
                .builder()
                .code(FileArchive("hello-function/build/distributions/hello-function.zip"))
                .handler("org.http4k.example.HelloServerlessHttp4k")
                .role(lambdaRole.arn())
                .runtime("java21")
                .timeout(15)
                .build()
        )

        val functionUrl = FunctionUrl(
            "hello-http4k-url", FunctionUrlArgs
                .builder()
                .functionName(function.name())
                .authorizationType("NONE")
                .build()
        )

        val publicUrl = functionUrl.functionUrl()

        context.export("publishedUrl", publicUrl)
    };
}
