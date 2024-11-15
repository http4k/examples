package domain

import TestData
import arrow.core.Either.Right
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import env.Buyer
import env.Environment
import env.Seller
import hexagonal.domain.MarketHub
import hexagonal.port.InMemoryNotifications
import hexagonal.port.InMemoryPhoneBook
import hexagonal.port.InMemoryUsers

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
            assertThat(
                hub.markDispatched(testData.sellerUser.id, testData.buyerUser.id, trackingNumber),
                equalTo(Right(0))
            )
        }
    }

    override val seller = object : Seller {
        override fun receivedMessage() =
            notifications.sent.first { it.first == testData.sellerPhone }.second
    }
}
