package actions

import model.Expense
import org.http4k.connect.Http4kConnectAction
import org.http4k.core.Body
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Uri
import org.http4k.format.Moshi.auto

@Http4kConnectAction
data class AddExpense(val name: String, val cost: Int) : ExpensesAction<Expense> {
    override fun toRequest() = Request(POST, uri())

    private fun uri() = Uri.of("/expenses/$name/$cost")

    override fun toResult(response: Response) = when {
        response.status.successful -> responseReportLens(response)
        else -> throw RuntimeException("got error " + response.status + " from ${uri()}")
    }
}

private val responseReportLens = Body.auto<Expense>().toLens()
