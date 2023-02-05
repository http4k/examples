package browser

import TestData
import env.Buyer
import env.Environment
import env.Seller
import org.http4k.client.OkHttp
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.format.Jackson.asA
import org.http4k.webdriver.Http4kWebDriver
import org.openqa.selenium.By

class BrowserEnvironment(
    testData: TestData,
    marketWebappUri: Uri,
    notificationsUri: Uri
) : Environment {
    private val notifications = SetBaseUriFrom(notificationsUri).then(OkHttp())
    private val market = SetBaseUriFrom(marketWebappUri).then(OkHttp())

    private val driver = Http4kWebDriver(market)

    override val buyer = object : Buyer {
        override fun marksItemDispatched(trackingNumber: String) {
            with(driver) {
                get(marketWebappUri)
                findElement(By.tagName("input"))!!.sendKeys(trackingNumber)
                findElement(By.tagName("button"))!!.submit()
            }
        }
    }

    override val seller = object : Seller {
        override fun receivedMessage() = asA<List<String>>(
            notifications(Request(GET, "/${testData.sellerPhone}")).bodyString()
        ).first()
    }
}
