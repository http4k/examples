package hexagonal.test

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import hexagonal.domain.Notifications
import hexagonal.domain.PhoneBook
import hexagonal.domain.User
import hexagonal.domain.Users

class InMemoryPhoneBook(
    vararg phone: Pair<String, String> = arrayOf(
        "Bob" to "1234567890",
        "Alice" to "0987654321"
    )
) : PhoneBook {
    private val numbers = phone.toMap()

    override fun numberFor(name: String) =
        numbers[name]?.let { Success(it) } ?: Failure(Exception("no user"))
}

class InMemoryUsers(vararg users: User = arrayOf(User(1, "Bob"), User(2, "Alice"))) : Users {
    private val users = users.map { it.id to it }.toMap()
    override fun nameFor(id: Int) = users[id]
}

class InMemoryNotifications : Notifications {
    private var counter = 0
    val sent = mutableListOf<Pair<String, String>>()

    override fun notify(phoneNumber: String, message: String): Result4k<Int, Exception> {
        sent += phoneNumber to message
        return Success(counter++)
    }
}
