package extending_http4k_connect

import extending_http4k_connect.model.Expense
import org.http4k.chaos.start
import org.http4k.connect.ChaosFake
import org.http4k.connect.storage.InMemory
import org.http4k.connect.storage.Storage
import org.http4k.routing.routes
import java.util.concurrent.atomic.AtomicInteger

class FakeExpensesSystem(
    storage: Storage<Expense> = Storage.InMemory(),
    counter: AtomicInteger = AtomicInteger()
) : ChaosFake() {
    override val app = routes(
        addExpense(storage, counter),
        expenseReport(storage)
    )
}

// if you run this you will see the (static) port which is allocated automatically by http4k-connect
fun main() {
    FakeExpensesSystem().start()
}
