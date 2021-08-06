package api

import MarketTests
import TestData

class ApiTest : MarketTests {
    override fun environmentFor(testData: TestData) = ApiEnvironment(testData)
}

