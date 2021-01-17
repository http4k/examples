package extending_http4k_connect

import dev.forkhandles.result4k.Result
import org.http4k.client.JavaHttpClient
import org.http4k.connect.RemoteFailure
import org.http4k.core.HttpHandler

/**
 * HTTP implementation of the Expenses system
 */
fun ExpensesSystem.Companion.Http(http: HttpHandler = JavaHttpClient()) =
    object : ExpensesSystem {
        override fun <R : Any> invoke(action: ExpensesAction<R>)
            : Result<R, RemoteFailure> = action.toResult(http(action.toRequest()))
    }
