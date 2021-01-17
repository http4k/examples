package extending_http4k_connect

import org.http4k.connect.ChaosFake
import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Moshi.auto
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

class FakeExpensesSystem : ChaosFake() {
    private val expenseLens = Body.auto<ExpenseReport>().toLens()

    override val app = routes(
        "/expenses/{name}" bind GET to { req: Request ->
            val name = req.path("name")!!
            val report = ExpenseReport(name, name
                .mapIndexed { index, c -> Expense("Expense $index", c.toInt()) })
            Response(OK).with(expenseLens of report)
        }
    )
}

// if you run this you will see the (static) port which is allocated automatically by http4k-connect
fun main() {
    FakeExpensesSystem().start()
}
