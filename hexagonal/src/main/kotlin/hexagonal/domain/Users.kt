package hexagonal.domain

interface Users {
    fun nameFor(id: Int): User?

    companion object
}

data class User(val id: Int, val name: String)

