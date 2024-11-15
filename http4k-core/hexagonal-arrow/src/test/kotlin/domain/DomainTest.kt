package domain

import MarketTests
import TestData

class DomainTest : MarketTests {
    override fun environmentFor(testData: TestData) = DomainEnvironment(testData)
}

