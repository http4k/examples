package extending_http4k_connect.actions

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import extending_http4k_connect.model.Expense
import org.http4k.connect.Http4kConnectAction
import org.http4k.connect.RemoteFailure
import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Uri
import org.http4k.format.Moshi.auto

@Http4kConnectAction
data class GetMyExpenses(val name: String) : ExpensesAction<ExpenseReport> {
    override fun toRequest() = Request(GET, uri())

    private fun uri() = Uri.of("/expenses/${name}")

    override fun toResult(response: Response) = when {
        response.status.successful -> Success(responseReportLens(response))
        else -> Failure(RemoteFailure(GET, uri(), response.status))
    }
}

data class ExpenseReport(val name: String, val expenses: Set<Expense>)

private val responseReportLens = Body.auto<ExpenseReport>().toLens()
