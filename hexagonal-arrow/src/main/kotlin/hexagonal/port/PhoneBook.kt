package hexagonal.port

import arrow.core.Either

interface PhoneBook {
    fun numberFor(name: String): Either<Exception, String>

    companion object
}
