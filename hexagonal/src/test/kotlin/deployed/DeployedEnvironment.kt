package hexagonal.test.deployed

import TestData
import com.natpryce.hamkrest.assertion.assertThat
import env.Buyer
import env.Environment
import env.Seller
import hexagonal.api.DispatchMessage
import org.http4k.client.OkHttp
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.format.Jackson.asA
import org.http4k.format.Jackson.asFormatString
import org.http4k.hamkrest.hasStatus

class DeployedEnvironment(
    testData: TestData,
    marketApiUri: Uri,
    notificationsUri: Uri
) : Environment {
    private val notifications = SetBaseUriFrom(notificationsUri).then(OkHttp())
    private val market = SetBaseUriFrom(marketApiUri).then(OkHttp())

    override val buyer = object : Buyer {
        override fun marksItemDispatched(trackingNumber: String) {
            assertThat(
                market(
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

    override val seller = object : Seller {
        override fun receivedMessage() = asA<List<String>>(
            notifications(Request(GET, "/${testData.sellerPhone}")).bodyString()
        ).first()
    }
}
