import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.NoSuchKeyException
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.UUID

class CatPicturesRepo(private val client: S3Client, private val bucket: String) {

    operator fun set(catId: UUID, photo: ByteArray) {
        client.putObject(
            PutObjectRequest.builder().bucket(bucket).key(catId.toString()).build(),
            RequestBody.fromBytes(photo)
        )
    }

    operator fun get(catId: UUID): ByteArray? = try {
        client.getObject {
            it.bucket(bucket)
            it.key(catId.toString())
        }.readAllBytes()
    } catch(e: NoSuchKeyException) {
        null
    }
}
