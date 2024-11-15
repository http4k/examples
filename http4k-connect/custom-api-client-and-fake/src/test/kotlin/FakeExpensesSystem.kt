import actions.ExpenseReport
import model.Expense
import org.http4k.chaos.ChaoticHttpHandler
import org.http4k.chaos.start
import org.http4k.connect.storage.InMemory
import org.http4k.connect.storage.Storage
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Moshi.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import java.util.concurrent.atomic.AtomicInteger

/**
 * This extends ChaosFake - which is an HttpHandler - thus making it substitutable for an HTTP client.
 * The storage abstraction is essentially string-keyed storage and can be swapped out with Redis, S3 etc..
 *
 * Splitting the Fake endpoints up into pure functions makes them modular, and easier to test/reason about.
 */
class FakeExpensesSystem(
    storage: Storage<Expense> = Storage.InMemory(),
    counter: AtomicInteger = AtomicInteger()
) : ChaoticHttpHandler() {
    override val app = routes(
        addExpenseHandler(storage, counter),
        expenseReportHandler(storage)
    )
}

/**
 * If you run this you will see the (static) port which is allocated automatically by http4k
 * in this case: 58611
 */
fun main() {
    FakeExpensesSystem().start()
}

fun addExpenseHandler(storage: Storage<Expense>, id: AtomicInteger): RoutingHttpHandler {
    val expenseLens = Body.auto<Expense>().toLens()

    return "/expenses/{name}/{cost}" bind Method.POST to { req: Request ->
        val name = req.path("name")!!
        val cost = req.path("cost")!!.toInt()
        val newExpense = Expense(id.incrementAndGet(), name, cost)
        storage[name + newExpense.id.toString()] = newExpense
        Response(CREATED).with(expenseLens of newExpense)
    }
}

fun expenseReportHandler(storage: Storage<Expense>): RoutingHttpHandler {
    val expenseReportLens = Body.auto<ExpenseReport>().toLens()

    return "/expenses/{name}" bind GET to { req: Request ->
        val name = req.path("name")!!
        val report = ExpenseReport(name, storage.keySet(name).map { storage[it]!! })
        Response(OK).with(expenseReportLens of report)
    }
}
