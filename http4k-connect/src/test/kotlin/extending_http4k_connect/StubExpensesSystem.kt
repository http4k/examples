package extending_http4k_connect

import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import extending_http4k_connect.actions.AddExpense
import extending_http4k_connect.actions.ExpenseReport
import extending_http4k_connect.actions.ExpensesAction
import extending_http4k_connect.actions.GetMyExpenses
import extending_http4k_connect.model.Expense
import org.http4k.connect.RemoteFailure
import org.http4k.connect.storage.InMemory
import org.http4k.connect.storage.Storage
import java.util.concurrent.atomic.AtomicInteger

/**
 * This is an in-memory stub of the ExpensesSystem which sites inside the HTTP boundary.
 */
class StubExpensesSystem : ExpensesSystem {
    private val counter = AtomicInteger()
    private val storage = Storage.InMemory<Expense>()

    override fun <R : Any> invoke(action: ExpensesAction<R>): Result<R, RemoteFailure> =
        when (action) {
            is AddExpense -> addExpense(action, storage, counter)
            is GetMyExpenses -> getMyExpenses(action, storage)
            else -> throw UnsupportedOperationException("Action ${action.javaClass.simpleName}")
        }
}

private fun <R : Any> getMyExpenses(action: GetMyExpenses, storage: Storage<Expense>): Result<R, RemoteFailure> =
    ExpenseReport(action.name, storage.keySet(action.name).map { storage[it]!! }.toSet()).asSuccess()

private fun <R : Any> addExpense(
    action: AddExpense,
    storage: Storage<Expense>,
    counter: AtomicInteger
): Result<R, RemoteFailure> {
    val newExpense = Expense(1, action.name, action.cost)
    storage[action.name + counter.incrementAndGet()] = newExpense
    return newExpense.asSuccess()
}

private fun <R : Any> Any.asSuccess() = Success(this) as Result<R, RemoteFailure>
