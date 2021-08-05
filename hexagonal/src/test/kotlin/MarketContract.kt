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

interface MarketContract {
    fun environmentFor(testData: TestData): Environment

    @Test
    fun `buyer is notified of seller dispatching item`() {
        val data = TestData()
        with(environmentFor(data)) {

            buyer.marksItemDispatched("12345")

            expectThat(seller.receivedTrackingId()).isEqualTo(
                """Hi ${data.buyerUser.name},
              |I have sent your item.
              |The tracking number is 12345!
              |Regards,
              |${data.sellerUser.name}""".trimMargin()
            )
        }
    }
}

