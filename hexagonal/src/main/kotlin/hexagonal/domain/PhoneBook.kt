package hexagonal.domain

import dev.forkhandles.result4k.Result4k

interface PhoneBook {
    fun numberFor(name: String): Result4k<String, Exception>

    companion object
}
