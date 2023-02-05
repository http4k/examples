package hexagonal.port

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right

class InMemoryPhoneBook(
    vararg phone: Pair<String, String> = arrayOf(
        "Bob" to "1234567890",
        "Alice" to "0987654321"
    )
) : PhoneBook {
    private val numbers = phone.toMap()

    override fun numberFor(name: String) =
        numbers[name]?.let { Right(it) } ?: Left(Exception("no user"))
}

class InMemoryUsers(vararg users: User = arrayOf(User(1, "Bob"), User(2, "Alice"))) : Users {
    private val users = users.associateBy { it.id }
    override fun nameFor(id: Int) = users[id]
}

class InMemoryNotifications : Notifications {
    private var counter = 0
    val sent = mutableListOf<Pair<String, String>>()

    override fun notify(phoneNumber: String, message: String): Either<Exception, Int> {
        sent += phoneNumber to message
        return Right(counter++)
    }
}
