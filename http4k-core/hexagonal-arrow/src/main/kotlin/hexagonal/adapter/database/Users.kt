package hexagonal.adapter.database

import hexagonal.port.User
import hexagonal.port.Users

fun Users.Companion.Database() = object : Users {
    override fun nameFor(id: Int): User? {
        TODO("\"Not implemented in this example. Use your imagination!\"")
    }
}
