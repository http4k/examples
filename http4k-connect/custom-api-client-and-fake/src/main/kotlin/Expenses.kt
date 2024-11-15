import actions.GetMyExpenses
import client.ExpensesSystem

/**
 * This is a simple domain wrapper for our client.ExpensesSystem adapter
 */
class Expenses(private val expensesSystem: ExpensesSystem) {
    fun countExpenses(name: String) = expensesSystem(GetMyExpenses(name)).expenses.size
}
