package extending_http4k_connect.actions

import dev.forkhandles.result4k.Result
import org.http4k.connect.Action
import org.http4k.connect.RemoteFailure

/**
 * This is the interface which will encapsulate all Actions
 * for the ExpensesSystem
 */
interface ExpensesAction<R> : Action<Result<R, RemoteFailure>>
