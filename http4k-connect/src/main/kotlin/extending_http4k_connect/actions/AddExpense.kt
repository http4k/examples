package extending_http4k_connect.actions

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import extending_http4k_connect.model.Expense
import org.http4k.connect.Http4kConnectAction
import org.http4k.connect.RemoteFailure
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
        response.status.successful -> Success(responseReportLens(response))
        else -> Failure(RemoteFailure(POST, uri(), response.status))
    }
}

private val responseReportLens = Body.auto<Expense>().toLens()
