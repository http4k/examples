import org.http4k.aws.AwsCredentials
import org.http4k.chaos.ChaosBehaviours.ReturnStatus
import org.http4k.client.JavaHttpClient
import org.http4k.connect.amazon.kms.FakeKMS
import org.http4k.connect.amazon.kms.Http
import org.http4k.connect.amazon.kms.KMS
import org.http4k.connect.amazon.kms.createKey
import org.http4k.connect.amazon.kms.model.CustomerMasterKeySpec.SYMMETRIC_DEFAULT
import org.http4k.connect.amazon.kms.model.KeyUsage.ENCRYPT_DECRYPT
import org.http4k.connect.amazon.core.model.Region
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.server.SunHttp
import org.http4k.server.asServer

/**
 * Shows using KMS adapter with a FakeKMS running locally, and using the chaos-mode to
 * blow up and test our adapter behaviour.
 */
fun main() {
    val fakeKMS = FakeKMS()
    val fakeKmsServer = fakeKMS.asServer(SunHttp(8000)).start()

    val http = SetBaseUriFrom(Uri.of("http://localhost:8000")).then(JavaHttpClient())
    val kms = KMS.Http(Region.of("us-east-1"), { AwsCredentials("access-key-id", "secret-key") }, http)

    println(kms.createKey(SYMMETRIC_DEFAULT, KeyUsage = ENCRYPT_DECRYPT))

    // make the KMS blow up!
    fakeKMS.misbehave(ReturnStatus(I_M_A_TEAPOT))

    println(kms.createKey(SYMMETRIC_DEFAULT, KeyUsage = ENCRYPT_DECRYPT))

    fakeKmsServer.stop()
}
