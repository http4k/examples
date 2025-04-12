package datastar

import kotlin.random.Random

data class User(
    val id: Int, val name: String, val login: String,
    val initial: String = name.first().uppercase(),
    val email: String = "${name.lowercase()}@http4k.org",
    val active: Boolean = Random.nextBoolean()
)

val users get() = listOf(
    "Alice" to "Today, 12:45 PM",
    "Bob" to "Never",
    "Charlie" to "Mar 28, 2025",
    "David" to "Yesterday, 5:15 PM",
    "Eddie" to "Today, 10:30 AM"
)
    .mapIndexed { i, (name, login) -> User(i + 1, name, login) }

