package domain

import MarketContract
import TestData

class DomainTest : MarketContract {
    override fun environmentFor(testData: TestData) = DomainEnvironment(testData)
}

