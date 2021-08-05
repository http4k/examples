package hexagonal.test.deployed

import hexagonal.test.MarketContract
import hexagonal.test.TestData
import org.http4k.core.Uri

class DeployedTest : MarketContract {
    override fun environmentFor(testData: TestData) = DeployedEnvironment(
        testData,
        Uri.of("https://marketapi"),
        Uri.of("https://notifications")
    )
}

