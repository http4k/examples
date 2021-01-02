package com.example

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import org.http4k.aws.AwsCredentials
import org.http4k.client.JavaHttpClient
import org.http4k.connect.amazon.kms.Http
import org.http4k.connect.amazon.kms.KMS
import org.http4k.connect.amazon.kms.encrypt
import org.http4k.connect.amazon.model.Base64Blob
import org.http4k.connect.amazon.model.EncryptionAlgorithm.SYMMETRIC_DEFAULT
import org.http4k.connect.amazon.model.KMSKeyId
import org.http4k.connect.amazon.model.Region
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun KMSEncrypt(keyId: KMSKeyId, kmsHttp: HttpHandler, region: Region): HttpHandler {
    val kms = KMS.Http(
        region, { AwsCredentials("accesskey", "secret") },
        kmsHttp
    )
    return routes("/{value}" bind GET to {
        when (val result = kms.encrypt(keyId, Base64Blob.encoded(it.path("value")!!), SYMMETRIC_DEFAULT)) {
            is Success -> Response(OK).body(result.value.CiphertextBlob.decoded())
            is Failure -> Response(result.reason.status)
        }
    })
}

fun main() {
    KMSEncrypt(KMSKeyId.of("kmsKeyId"), JavaHttpClient(), Region.of("us-east-1")).asServer(SunHttp(8080)).start()
}
