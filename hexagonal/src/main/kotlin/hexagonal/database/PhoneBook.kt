package hexagonal.database

import dev.forkhandles.result4k.Result4k
import hexagonal.domain.PhoneBook

fun PhoneBook.Companion.Database() = object : PhoneBook {
    override fun numberFor(name: String): Result4k<String, Exception> {
        TODO("Not yet implemented")
    }
}

