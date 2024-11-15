package client

import actions.ExpensesAction
import org.http4k.connect.Http4kConnectApiClient

/**
 * The main system interface. The Annotation is used by Kapt
 * to generate the friendly extension functions.
 */
@Http4kConnectApiClient
interface ExpensesSystem {
    operator fun <R : Any> invoke(action: ExpensesAction<R>): R

    companion object
}

