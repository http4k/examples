package environment

import com.example.S3BucketContents
import org.http4k.aws.AwsCredentialScope
import org.http4k.aws.AwsCredentials
import org.http4k.client.JavaHttpClient
import org.http4k.connect.amazon.s3.BucketKey
import org.http4k.connect.amazon.s3.BucketName
import org.http4k.connect.amazon.s3.FakeS3
import org.http4k.connect.amazon.s3.Http
import org.http4k.connect.amazon.s3.S3
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
    val s3Bucket = S3.Bucket.Http(BucketName("mybucket"),
        AwsCredentialScope("us-east-1", "s3"), { AwsCredentials("accesskey", "secret") },
        s3Http
        )

    // create the bucket and the files to go in it...
    s3Bucket.apply {
        s3Bucket.create()
        set(BucketKey("file1"), "hello".byteInputStream())
        set(BucketKey("file2"), "there".byteInputStream())
    }
}
