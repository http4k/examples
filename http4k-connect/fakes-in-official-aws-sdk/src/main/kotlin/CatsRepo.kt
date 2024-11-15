import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.UUID

class CatsRepo(private val client: DynamoDbClient, private val tableName: String) {

    operator fun plusAssign(cat: Cat) {
        client.putItem {
            it.tableName(tableName)
            it.item(mapOf(
                "id" to AttributeValue.fromS(cat.id.toString()),
                "name" to AttributeValue.fromS(cat.name)
            ))
        }
    }

    operator fun get(id: UUID): Cat? {
        val item = client.getItem {
            it.tableName(tableName)
            it.key(mapOf("id" to AttributeValue.fromS(id.toString())))
        }.item() ?: return null

        return Cat(
            id = UUID.fromString(item.getValue("id").s()),
            name = item.getValue("name").s()
        )
    }
}
