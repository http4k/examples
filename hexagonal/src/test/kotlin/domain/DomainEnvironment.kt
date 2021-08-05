package hexagonal.test.domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import hexagonal.domain.DispatchResult.Ok
import hexagonal.domain.MarketHub
import hexagonal.test.InMemoryNotifications
import hexagonal.test.InMemoryPhoneBook
import hexagonal.test.InMemoryUsers
import hexagonal.test.TestData
import hexagonal.test.env.Buyer
import hexagonal.test.env.Environment
import hexagonal.test.env.Seller

class DomainEnvironment(testData: TestData) : Environment {
    private val notifications = InMemoryNotifications()
    private val hub = MarketHub(
        InMemoryPhoneBook(
            testData.buyerUser.name to testData.buyerPhone,
            testData.sellerUser.name to testData.sellerPhone
        ),
        InMemoryUsers(),
        notifications
    )

    override val buyer = object : Buyer {
        override fun marksItemDispatched(trackingNumber: String) {
            assertThat(
                hub.markDispatched(testData.sellerUser.id, testData.buyerUser.id, trackingNumber),
                equalTo(Ok)
            )
        }
    }

    override val seller: Seller = object : Seller {
        override fun receivedTrackingId(): String {
            println(notifications.sent)
            return notifications.sent.first { it.first == testData.sellerPhone }.second
        }
    }
}
