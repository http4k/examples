import io.kotest.matchers.shouldBe
import org.http4k.aws.AwsSdkClient
import org.http4k.chaos.start
import org.http4k.connect.amazon.dynamodb.FakeDynamoDb
import org.junit.Test
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import java.net.URI
import java.util.UUID

private fun DynamoDbClient.catsRepo(): CatsRepo {
    createTable {
        it.tableName("cats")
        it.attributeDefinitions(
            AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.S).build()
        )
        it.keySchema(
            KeySchemaElement.builder().attributeName("id").keyType(KeyType.HASH).build()
        )
    }
    return CatsRepo(this, "cats")
}

class CatsRepoTest {

    @Test
    fun `set and get - with in-memory server`() {
        val testObj = DynamoDbClient.builder()
            .httpClient(AwsSdkClient(FakeDynamoDb()))
            .credentialsProvider { AwsBasicCredentials.create("key_id", "secret_key") }
            .build()
            .catsRepo()

        val cat = Cat(UUID.randomUUID(), "Kratos")

        testObj += cat
        testObj[cat.id] shouldBe cat
    }

    @Test
    fun `set and get - with running server`() {
        val server = FakeDynamoDb().start()

        val testObj = DynamoDbClient.builder()
            .endpointOverride(URI("http://localhost:${server.port()}"))
            .credentialsProvider { AwsBasicCredentials.create("key_id", "secret_key") }
            .build()
            .catsRepo()

        val cat = Cat(UUID.randomUUID(), "Kratos")

        testObj += cat
        testObj[cat.id] shouldBe cat
    }
}
