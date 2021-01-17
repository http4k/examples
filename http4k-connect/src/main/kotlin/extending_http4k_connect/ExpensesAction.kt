package extending_http4k_connect

import org.http4k.connect.Action

/**
 * This is the interface which will encapsulate all Actions
 * for the ExpensesSystem
 */
interface ExpensesAction<T> : Action<T>
