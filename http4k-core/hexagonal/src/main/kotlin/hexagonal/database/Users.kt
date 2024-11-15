package hexagonal.database

import hexagonal.domain.User
import hexagonal.domain.Users

fun Users.Companion.Database() = object : Users {
    override fun nameFor(id: Int): User? {
        TODO("Not yet implemented")
    }
}
