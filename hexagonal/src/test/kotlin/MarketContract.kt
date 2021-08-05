import env.Environment
import hexagonal.domain.User
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestData {
    val buyerUser = User(1, "Bob")
    val buyerPhone = "01234567890"

    val sellerUser = User(2, "Alice")
    val sellerPhone = "01234567890"
}

interface MarketContract {
    fun environmentFor(testData: TestData): Environment

    @Test
    fun `seller is notified of buyer dispatching item`() {
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

