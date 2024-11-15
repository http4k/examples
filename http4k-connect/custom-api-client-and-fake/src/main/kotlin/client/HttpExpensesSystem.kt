package client

import actions.ExpensesAction
import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler

/**
 * HTTP implementation of the Expenses system
 */
fun ExpensesSystem.Companion.Http(http: HttpHandler = JavaHttpClient()) =
    object : ExpensesSystem {
        override fun <R : Any> invoke(action: ExpensesAction<R>)
            : R = action.toResult(http(action.toRequest()))
    }
