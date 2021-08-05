package api

import MarketContract
import TestData

class ApiTest : MarketContract {
    override fun environmentFor(testData: TestData) = ApiEnvironment(testData)
}

