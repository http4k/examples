package hexagonal.test.domain

import hexagonal.test.MarketContract
import hexagonal.test.TestData

class DomainTest : MarketContract {
    override fun environmentFor(testData: TestData) = DomainEnvironment(testData)
}

