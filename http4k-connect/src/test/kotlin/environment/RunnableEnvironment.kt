package environment

import com.example.S3BucketContents
import org.http4k.aws.AwsCredentialScope
import org.http4k.aws.AwsCredentials
import org.http4k.client.JavaHttpClient
import org.http4k.connect.amazon.model.BucketKey
import org.http4k.connect.amazon.model.BucketName
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
    val fakeS3 = FakeS3()
    val server = fakeS3.start()

    val s3Http = createClientPointedAtLocalFakeS3(server)

    createS3BucketAndFiles(s3Http)

    S3BucketContents(s3Http).asServer(SunHttp(8080)).start()
}

private fun createClientPointedAtLocalFakeS3(server: Http4kServer) =
    ClientFilters.SetBaseUriFrom(Uri.of("http://localhost:${server.port()}"))
        .then(JavaHttpClient())

private fun createS3BucketAndFiles(s3Http: HttpHandler) {
    val region = Region.of("us-east-1")

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
