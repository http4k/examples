package extending_http4k_connect

import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import extending_http4k_connect.actions.getMyExpenses

/**
 * This is a simple domain wrapper for our ExpensesSystem adapter
 */
class ExpensesClient(private val expensesSystem: ExpensesSystem) {
    fun countExpenses(name: String) = expensesSystem.getMyExpenses(name)
        .map { it.expenses.size }
        .recover { -1 }
}
