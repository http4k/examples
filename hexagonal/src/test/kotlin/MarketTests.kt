import env.Environment
import hexagonal.domain.User
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestData {
    val sellerUser = User(1, "Alice")
    val sellerPhone = "01234567890"

    val buyerUser = User(2, "Bob")
    val buyerPhone = "01234567890"
}

interface MarketTests {
    fun environmentFor(testData: TestData): Environment

    @Test
    fun `buyer is notified of seller dispatching item`() {
        val data = TestData()

        val trackingNumber = "12345"

        with(environmentFor(data)) {

            buyer.marksItemDispatched(trackingNumber)

            expectThat(seller.receivedMessage()).isEqualTo(
                """Hi ${data.buyerUser.name},
              |I have sent your item.
              |The tracking number is $trackingNumber!
              |Regards,
              |${data.sellerUser.name}""".trimMargin()
            )
        }
    }
}

