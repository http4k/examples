package browser

import MarketTests
import TestData
import org.http4k.core.Uri
import org.junit.jupiter.api.Disabled

@Disabled("Won't work unless deployed with a webapp!")
class BrowserTest : MarketTests {
    override fun environmentFor(testData: TestData) = BrowserEnvironment(
        testData,
        Uri.of("https://marketapi"),
        Uri.of("https://notifications")
    )
}

