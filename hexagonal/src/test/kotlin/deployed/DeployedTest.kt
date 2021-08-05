package deployed

import MarketContract
import TestData
import org.http4k.core.Uri
import org.junit.jupiter.api.Disabled

@Disabled("Won't work unless deployed!")
class DeployedTest : MarketContract {
    override fun environmentFor(testData: TestData) = DeployedEnvironment(
        testData,
        Uri.of("https://marketapi"),
        Uri.of("https://notifications")
    )
}

