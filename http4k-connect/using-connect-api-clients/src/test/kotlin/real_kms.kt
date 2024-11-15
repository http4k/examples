import org.http4k.aws.AwsCredentials
import org.http4k.client.JavaHttpClient
import org.http4k.connect.amazon.kms.Http
import org.http4k.connect.amazon.kms.KMS
import org.http4k.connect.amazon.kms.createKey
import org.http4k.connect.amazon.kms.model.CustomerMasterKeySpec.SYMMETRIC_DEFAULT
import org.http4k.connect.amazon.kms.model.KeyUsage.ENCRYPT_DECRYPT
import org.http4k.connect.amazon.core.model.Region
import org.http4k.filter.debug

/**
 * Shows using KMS adapter against the real KMS with a real HTTP client.
 */
fun main() {
    val http = JavaHttpClient()

    val awsCredentials = AwsCredentials("put-your-access-key-id-here", "put-your-secret-key-key-here")
    val kms = KMS.Http(Region.of("us-east-1"), { awsCredentials }, http.debug())

    println(kms.createKey(SYMMETRIC_DEFAULT, KeyUsage = ENCRYPT_DECRYPT))
}
