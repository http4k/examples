package hexagonal.adapter.database

import arrow.core.Either
import hexagonal.port.PhoneBook

fun PhoneBook.Companion.Database() = object : PhoneBook {
    override fun numberFor(name: String): Either<Exception, String> {
        TODO("Not implemented in this example. Use your imagination!")
    }
}

