package extending_http4k_connect

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.get
import io.mockk.every
import io.mockk.mockk
import org.http4k.chaos.ChaosBehaviours.ReturnStatus
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.junit.jupiter.api.Test

class ExpensesSystemTest {

    @Test
    fun `can add and retrieve expenses`() {
        val fakeExpensesSystem = FakeExpensesSystem()
        val expensesSystem = ExpensesSystem.Http(fakeExpensesSystem)
        val added1 = expensesSystem.addExpense("Bob", 123).get() as Expense
        expensesSystem.addExpense("Alice", 456).get() as Expense
        val added3 = expensesSystem.addExpense("Bob", 789).get() as Expense
        assertThat(
            expensesSystem.getMyExpenses("Bob"), equalTo(
                Success(ExpenseReport("Bob", setOf(added1, added3)))
            )
        )
    }

    @Test
    fun `can add and retrieve expenses - stub`() {
        val expensesSystem = StubExpensesSystem()
        val added1 = expensesSystem.addExpense("Bob", 123).get() as Expense
        expensesSystem.addExpense("Alice", 456).get() as Expense
        val added3 = expensesSystem.addExpense("Bob", 789).get() as Expense
        assertThat(
            expensesSystem.getMyExpenses("Bob"), equalTo(
                Success(ExpenseReport("Bob", setOf(added1, added3)))
            )
        )
    }

    @Test
    fun `can count expenses with client and fake`() {
        val fakeExpensesSystem = FakeExpensesSystem()
        val expensesSystem = ExpensesSystem.Http(fakeExpensesSystem)
        expensesSystem.addExpense("Bob", 123).get() as Expense
        expensesSystem.addExpense("Alice", 456).get() as Expense
        expensesSystem.addExpense("Bob", 789).get() as Expense
        assertThat(ExpensesClient(expensesSystem).countExpenses("Bob"), equalTo(2))
    }

    @Test
    fun `can find out what happens when get expenses call fails`() {
        val fakeExpensesSystem = FakeExpensesSystem()
        val expensesSystem = ExpensesSystem.Http(fakeExpensesSystem)
        fakeExpensesSystem.misbehave(ReturnStatus(I_M_A_TEAPOT))
        assertThat(ExpensesClient(expensesSystem).countExpenses("Bob"), equalTo(-1))
    }

    @Test
    fun `can count expenses with client and mock - using convenience method`() {
        val mockExpensesSystem = mockk<ExpensesSystem>()

        every { mockExpensesSystem.getMyExpenses("Bob") } returns
            Success(ExpenseReport("Bob", setOf(Expense(1, "Expense 0", 66))))

        assertThat(ExpensesClient(mockExpensesSystem).countExpenses("Bob"), equalTo(1))
    }

    @Test
    fun `can count expenses with client and mock using invoke()`() {
        val mockExpensesSystem = mockk<ExpensesSystem>()

        every { mockExpensesSystem(any<GetMyExpenses>()) } returns
            Success(ExpenseReport("Bob", setOf(Expense(1, "Expense 0", 66))))

        assertThat(ExpensesClient(mockExpensesSystem).countExpenses("Bob"), equalTo(1))
    }
}
