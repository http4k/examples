package extending_http4k_connect

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import dev.forkhandles.result4k.Success
import io.mockk.every
import io.mockk.mockk
import org.http4k.chaos.ChaosBehaviours.ReturnStatus
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.junit.jupiter.api.Test

class ExpensesSystemTest {

    private val fakeExpensesSystem = FakeExpensesSystem()
    private val expensesSystem = ExpensesSystem.Http(fakeExpensesSystem)

    @Test
    fun `can get my expenses using HTTP fake`() {
        assertThat(
            expensesSystem.getMyExpenses("Bob"), equalTo(
                Success(
                    ExpenseReport(
                        "Bob",
                        listOf(
                            Expense("Expense 0", 66),
                            Expense("Expense 1", 111),
                            Expense("Expense 2", 98)
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `can count expenses with client and fake`() {
        assertThat(ExpensesClient(expensesSystem).countExpenses("Bob"), equalTo(3))
    }

    @Test
    fun `can find out what happens when get expenses call fails`() {
        fakeExpensesSystem.misbehave(ReturnStatus(I_M_A_TEAPOT))
        assertThat(ExpensesClient(expensesSystem).countExpenses("Bob"), equalTo(-1))
    }

    @Test
    fun `can count expenses with client and mock - using convenience method`() {
        val mockExpensesSystem = mockk<ExpensesSystem>()

        every { mockExpensesSystem.getMyExpenses("Bob") } returns
            Success(ExpenseReport("Bob", listOf(Expense("Expense 0", 66))))

        assertThat(ExpensesClient(mockExpensesSystem).countExpenses("Bob"), equalTo(1))
    }

    @Test
    fun `can count expenses with client and mock using invoke()`() {
        val mockExpensesSystem = mockk<ExpensesSystem>()

        every { mockExpensesSystem(any<GetMyExpenses>()) } returns
            Success(ExpenseReport("Bob", listOf(Expense("Expense 0", 66))))

        assertThat(ExpensesClient(mockExpensesSystem).countExpenses("Bob"), equalTo(1))
    }
}
