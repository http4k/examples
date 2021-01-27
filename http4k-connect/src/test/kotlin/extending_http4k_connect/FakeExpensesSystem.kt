package extending_http4k_connect

import org.http4k.connect.ChaosFake
import org.http4k.routing.routes

class FakeExpensesSystem(system: ExpensesSystem = StubExpensesSystem()) : ChaosFake() {
    override val app = routes(
        addExpense(system),
        expenseReport(system)
    )
}

// if you run this you will see the (static) port which is allocated automatically by http4k-connect
fun main() {
    FakeExpensesSystem().start()
}
