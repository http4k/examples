package environment

import com.example.KMSEncrypt
import com.example.S3BucketContents
import dev.forkhandles.result4k.valueOrNull
import org.http4k.aws.AwsCredentialScope
import org.http4k.aws.AwsCredentials
import org.http4k.client.JavaHttpClient
import org.http4k.connect.amazon.kms.FakeKMS
import org.http4k.connect.amazon.kms.Http
import org.http4k.connect.amazon.kms.KMS
import org.http4k.connect.amazon.kms.createKey
import org.http4k.connect.amazon.model.BucketKey
import org.http4k.connect.amazon.model.BucketName
import org.http4k.connect.amazon.model.CustomerMasterKeySpec.SYMMETRIC_DEFAULT
import org.http4k.connect.amazon.model.KMSKeyId
import org.http4k.connect.amazon.model.Region
import org.http4k.connect.amazon.s3.FakeS3
import org.http4k.connect.amazon.s3.Http
import org.http4k.connect.amazon.s3.S3
import org.http4k.connect.amazon.s3.S3Bucket
import org.http4k.connect.amazon.s3.createBucket
import org.http4k.core.HttpHandler
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val region = Region.of("us-east-1")

    val fakeS3 = FakeS3()
    val s3Server = fakeS3.start()
    val s3Http = createClientPointedAtLocalFake(s3Server)
    createS3BucketAndFiles(s3Http, region)
    S3BucketContents(s3Http).asServer(SunHttp(8080)).start()

    val fakeKms = FakeKMS()
    val kmsServer = fakeKms.start()
    val kmsHttp = createClientPointedAtLocalFake(kmsServer)
    val keyId = createKMSKey(kmsHttp, region)
    KMSEncrypt(keyId, kmsHttp).asServer(SunHttp(9090)).start()
}

private fun createClientPointedAtLocalFake(server: Http4kServer) =
    ClientFilters.SetBaseUriFrom(Uri.of("http://localhost:${server.port()}"))
        .then(JavaHttpClient())

private fun createS3BucketAndFiles(s3Http: HttpHandler, region: Region) {
    val s3 = S3.Http(
        AwsCredentialScope(region.value, "s3"), { AwsCredentials("accesskey", "secret") },
        s3Http
    )

    // create the bucket
    val bucketName = BucketName.of("mybucket")
    s3.createBucket(bucketName, region)

    val s3Bucket = S3Bucket.Http(bucketName,
        AwsCredentialScope("us-east-1", "s3"), { AwsCredentials("accesskey", "secret") },
        s3Http
    )

    s3Bucket[BucketKey.of("file1")] = "hello".byteInputStream()
    s3Bucket[BucketKey.of("file2")] = "there".byteInputStream()
}

private fun createKMSKey(kmsHttp: HttpHandler, region: Region): KMSKeyId {
    val kms = KMS.Http(
        AwsCredentialScope(region.value, "kms"), { AwsCredentials("accesskey", "secret") },
        kmsHttp
    )

    return kms.createKey(SYMMETRIC_DEFAULT).valueOrNull()!!.KeyMetadata.KeyId
}
