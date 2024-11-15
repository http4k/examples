import io.kotest.matchers.shouldBe
import org.http4k.aws.AwsSdkClient
import org.http4k.chaos.defaultLocalUri
import org.http4k.chaos.start
import org.http4k.client.JavaHttpClient
import org.http4k.connect.amazon.s3.FakeS3
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.junit.Test
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.services.s3.S3Client
import java.util.UUID

class CatPicturesRepoTest {

    private fun S3Client.catPicturesRepo(): CatPicturesRepo {
        createBucket {
            it.bucket("cats")
        }
        return CatPicturesRepo(this, "cats")
    }

    @Test
    fun `set and get - with in-memory server`() {
        val client = S3Client.builder()
            .httpClient(AwsSdkClient(FakeS3()))
            .credentialsProvider { AwsBasicCredentials.create("key_id", "secret_key") }
            .build()
            .catPicturesRepo()

        val catId = UUID.randomUUID()

        // set
        client[catId] = "lolcats".encodeToByteArray()

        // get
        client[catId]?.decodeToString() shouldBe "lolcats"
    }

    @Test
    fun `set and get - with running server`() {
        FakeS3().start()

        val client = S3Client.builder()
            .httpClient(AwsSdkClient(ClientFilters.SetHostFrom(FakeS3::class.defaultLocalUri).then(JavaHttpClient())))
            .credentialsProvider { AwsBasicCredentials.create("key_id", "secret_key") }
            .build()
            .catPicturesRepo()

        val catId = UUID.randomUUID()

        // set
        client[catId] = "lolcats".encodeToByteArray()

        // get
        client[catId]?.decodeToString() shouldBe "lolcats"
    }
}
