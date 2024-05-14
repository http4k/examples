package domain

import TestData
import env.Buyer
import env.Environment
import env.Seller
import fakes.InMemoryNotifications
import fakes.InMemoryPhoneBook
import fakes.InMemoryUsers
import hexagonal.domain.DispatchResult.Ok
import hexagonal.domain.MarketHub
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class DomainEnvironment(testData: TestData) : Environment {
    private val notifications = InMemoryNotifications()
    private val hub = MarketHub(
        InMemoryPhoneBook(
            testData.buyerUser.name to testData.buyerPhone,
            testData.sellerUser.name to testData.sellerPhone
        ),
        InMemoryUsers(testData.buyerUser, testData.sellerUser),
        notifications
    )

    override val buyer = object : Buyer {
        override fun marksItemDispatched(trackingNumber: String) {
            expectThat(
                hub.markDispatched(testData.sellerUser.id, testData.buyerUser.id, trackingNumber),
            ).isEqualTo(Ok)
        }
    }

    override val seller = object : Seller {
        override fun receivedMessage() =
            notifications.sent.first { it.first == testData.sellerPhone }.second
    }
}
