import org.http4k.aws.AwsCredentials
import org.http4k.chaos.ChaosBehaviours.ReturnStatus
import org.http4k.connect.amazon.kms.FakeKMS
import org.http4k.connect.amazon.kms.Http
import org.http4k.connect.amazon.kms.KMS
import org.http4k.connect.amazon.kms.createKey
import org.http4k.connect.amazon.kms.model.CustomerMasterKeySpec.SYMMETRIC_DEFAULT
import org.http4k.connect.amazon.kms.model.KeyUsage.ENCRYPT_DECRYPT
import org.http4k.connect.amazon.core.model.Region
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.http4k.filter.debug

/**
 * Shows using KMS adapter with a FakeKMS as a substitute for a real HttpHandler, and using the chaos-mode to
 * blow up and test our adapter behaviour.
 */
fun main() {
    val fakeKms = FakeKMS() // is an HttpHandler!

    val kms = KMS.Http(Region.of("us-east-1"), { AwsCredentials("access-key-id", "secret-key") }, fakeKms.debug())

    println(kms.createKey(SYMMETRIC_DEFAULT, KeyUsage = ENCRYPT_DECRYPT))

    // make the KMS blow up!
    fakeKms.misbehave(ReturnStatus(I_M_A_TEAPOT))

    println(kms.createKey(SYMMETRIC_DEFAULT, KeyUsage = ENCRYPT_DECRYPT))
}
