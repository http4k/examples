package com.example

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import org.http4k.aws.AwsCredentialScope
import org.http4k.aws.AwsCredentials
import org.http4k.client.JavaHttpClient
import org.http4k.connect.amazon.model.BucketName
import org.http4k.connect.amazon.s3.Http
import org.http4k.connect.amazon.s3.S3
import org.http4k.connect.amazon.s3.S3Bucket
import org.http4k.connect.amazon.s3.action.ListKeys
import org.http4k.connect.amazon.s3.listKeys
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun S3BucketContents(s3Http: HttpHandler): HttpHandler {
    val s3 = S3Bucket.Http(BucketName.of("mybucket"),
        AwsCredentialScope("us-east-1", "s3"), { AwsCredentials("accesskey", "secret") },
        s3Http
    )
    return routes("/" bind GET to {
        when (val result = s3.listKeys()) {
            is Success -> Response(OK).body(result.value.joinToString("\n") { it.value })
            is Failure -> Response(result.reason.status)
        }
    })
}

fun main() {
    S3BucketContents(JavaHttpClient()).asServer(SunHttp(8080)).start()
}
