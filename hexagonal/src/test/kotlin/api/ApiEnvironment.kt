package api

import TestData
import com.natpryce.hamkrest.assertion.assertThat
import env.Buyer
import env.Environment
import env.Seller
import fakes.FakeNotificationsServer
import fakes.InMemoryPhoneBook
import fakes.InMemoryUsers
import hexagonal.api.DispatchMessage
import hexagonal.api.MarketApi
import hexagonal.domain.Notifications
import hexagonal.http.Http
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.format.Jackson.asFormatString
import org.http4k.hamkrest.hasStatus

class ApiEnvironment(testData: TestData) : Environment {
    private val notifications = FakeNotificationsServer()
    private val phoneBook = InMemoryPhoneBook(
        testData.buyerUser.name to testData.buyerPhone,
        testData.sellerUser.name to testData.sellerPhone
    )

    private val api = MarketApi(
        phoneBook,
        InMemoryUsers(testData.buyerUser, testData.sellerUser),
        Notifications.Http(Uri.of("https://notifications"), notifications)
    )

    override val buyer = object : Buyer {
        override fun marksItemDispatched(trackingNumber: String) {
            assertThat(
                api(
                    Request(POST, "/dispatch")
                        .body(
                            asFormatString(
                                DispatchMessage(
                                    testData.sellerUser.id, testData.buyerUser.id, trackingNumber
                                )
                            )
                        )
                ), hasStatus(OK)
            )
        }
    }

    override val seller = object : Seller {
        override fun receivedMessage() =
            notifications.notificationsFor(testData.sellerPhone)
                .first { it.first == testData.sellerPhone }.second
    }
}
