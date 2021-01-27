package extending_http4k_connect

import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import extending_http4k_connect.actions.ExpenseReport
import extending_http4k_connect.model.Expense
import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Moshi.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.path

fun addExpense(system: ExpensesSystem): RoutingHttpHandler {
    val expenseLens = Body.auto<Expense>().toLens()

    return "/expenses/{name}/{cost}" bind POST to { req: Request ->
        system.addExpense(req.path("name")!!, req.path("cost")!!.toInt())
            .map { Response(CREATED).with(expenseLens of it) }
            .recover { Response(it.status).body(it.message ?: "") }
    }
}

fun expenseReport(system: ExpensesSystem): RoutingHttpHandler {
    val expenseReportLens = Body.auto<ExpenseReport>().toLens()

    return "/expenses/{name}" bind GET to { req: Request ->
        system.getMyExpenses(req.path("name")!!)
            .map { Response(OK).with(expenseReportLens of it) }
            .recover { Response(it.status).body(it.message ?: "") }
    }
}
