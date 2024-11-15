import actions.AddExpense
import actions.ExpenseReport
import actions.ExpensesAction
import actions.GetMyExpenses
import client.ExpensesSystem
import model.Expense
import org.http4k.connect.storage.InMemory
import org.http4k.connect.storage.Storage
import java.util.concurrent.atomic.AtomicInteger

class StubExpensesSystem(private val storage: Storage<List<Expense>> = Storage.InMemory()) : ExpensesSystem {

    private val counter = AtomicInteger(0)

    override fun <R : Any> invoke(action: ExpensesAction<R>): R = when (action) {
        is AddExpense -> addExpense(action, storage, counter) as R
        is GetMyExpenses -> getMyExpenses(action, storage) as R
        else -> error("Unknown action")
    }
}

private fun addExpense(action: AddExpense, storage: Storage<List<Expense>>, counter: AtomicInteger): Expense {
    val expense = Expense(counter.getAndIncrement(), action.name, action.cost)
    storage[expense.name] = listOf(expense) + (storage[expense.name] ?: emptyList())
    return expense
}

private fun getMyExpenses(action: GetMyExpenses, storage: Storage<List<Expense>>) =
    ExpenseReport(action.name, storage[action.name] ?: emptyList())
