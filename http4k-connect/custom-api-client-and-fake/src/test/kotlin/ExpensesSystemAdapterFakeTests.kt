import actions.ExpenseReport
import actions.addExpense
import actions.getMyExpenses
import client.ExpensesSystem
import client.Http
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.throws
import org.http4k.chaos.ChaosBehaviours.ReturnStatus
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.junit.jupiter.api.Test

/**
 * These tests use the FakeExpensesSystem and the our http4k-connect Adapter.
 *
 * Note the test which uses the Chaos behaviour of the
 */
class ExpensesSystemAdapterFakeTests {

    private val fakeExpensesSystem = FakeExpensesSystem()
    private val expensesSystem = ExpensesSystem.Http(fakeExpensesSystem)

    @Test
    fun `can add and retrieve expenses`() {
        val added1 = expensesSystem.addExpense("Bob", 123)
        expensesSystem.addExpense("Alice", 456)
        val added3 = expensesSystem.addExpense("Bob", 789)
        assertThat(
            expensesSystem.getMyExpenses("Bob"), equalTo(
                ExpenseReport("Bob", listOf(added3, added1))
            )
        )
    }

    @Test
    fun `can add and retrieve expenses - stub`() {
        val added1 = expensesSystem.addExpense("Bob", 123)
        expensesSystem.addExpense("Alice", 456)
        val added3 = expensesSystem.addExpense("Bob", 789)
        assertThat(
            expensesSystem.getMyExpenses("Bob"), equalTo(
                ExpenseReport("Bob", listOf(added3, added1))
            )
        )
    }

    @Test
    fun `can count expenses with client`() {
        expensesSystem.addExpense("Bob", 123)
        expensesSystem.addExpense("Alice", 456)
        expensesSystem.addExpense("Bob", 789)
        assertThat(Expenses(expensesSystem).countExpenses("Bob"), equalTo(2))
    }

    @Test
    fun `can find out what happens when get expenses call fails`() {
        fakeExpensesSystem.misbehave(ReturnStatus(I_M_A_TEAPOT))
        assertThat({ Expenses(expensesSystem).countExpenses("Bob") }, throws<RuntimeException>())
    }
}
