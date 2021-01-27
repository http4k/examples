package extending_http4k_connect

import org.http4k.connect.storage.Storage
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
import java.util.Random

fun addExpense(storage: Storage<Expense>): RoutingHttpHandler {
    val expenseLens = Body.auto<Expense>().toLens()

    return "/expenses/{name}/{cost}" bind POST to { req: Request ->
        val name = req.path("name")!!
        val cost = req.path("cost")!!.toInt()
        val newExpense = Expense(Random().nextInt(), name, cost)
        storage[name + newExpense.id.toString()] = newExpense
        Response(CREATED).with(expenseLens of newExpense)
    }
}

fun expenseReport(storage: Storage<Expense>): RoutingHttpHandler {
    val expenseReportLens = Body.auto<ExpenseReport>().toLens()

    return "/expenses/{name}" bind GET to { req: Request ->
        val name = req.path("name")!!
        val report = ExpenseReport(name, storage.keySet(name).map { storage[it]!! }.toSet())
        Response(OK).with(expenseReportLens of report)
    }
}
