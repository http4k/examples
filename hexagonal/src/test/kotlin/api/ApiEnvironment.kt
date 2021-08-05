package api

import com.natpryce.hamkrest.assertion.assertThat
import hexagonal.api.DispatchMessage
import hexagonal.api.MarketApi
import hexagonal.domain.Notifications
import hexagonal.external.Http
import hexagonal.test.InMemoryPhoneBook
import hexagonal.test.InMemoryUsers
import hexagonal.test.TestData
import hexagonal.test.env.Buyer
import hexagonal.test.env.Environment
import hexagonal.test.env.Seller
import hexagonal.test.fakes.FakeNotificationsServer
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
                                    testData.buyerUser.id, testData.sellerUser.id, trackingNumber
                                )
                            )
                        )
                ), hasStatus(OK)
            )
        }
    }

    override val seller: Seller = object : Seller {
        override fun receivedTrackingId() =
            notifications.notificationsFor(testData.sellerPhone)
                .first { it.first == testData.sellerUser.name }.second
    }
}
