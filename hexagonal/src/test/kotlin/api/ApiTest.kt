package api

import hexagonal.test.MarketContract
import hexagonal.test.TestData

class ApiTest : MarketContract {
    override fun environmentFor(testData: TestData) = ApiEnvironment(testData)
}

