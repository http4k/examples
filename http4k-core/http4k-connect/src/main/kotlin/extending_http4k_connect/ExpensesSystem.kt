package extending_http4k_connect

import dev.forkhandles.result4k.Result
import extending_http4k_connect.actions.ExpensesAction
import org.http4k.connect.Http4kConnectAdapter
import org.http4k.connect.RemoteFailure

/**
 * The main system interface. The Annotation is used by Kapt
 * to generate the friendly extension functions.
 */
@Http4kConnectAdapter
interface ExpensesSystem {
    operator fun <R : Any> invoke(action: ExpensesAction<R>): Result<R, RemoteFailure>

    companion object
}

