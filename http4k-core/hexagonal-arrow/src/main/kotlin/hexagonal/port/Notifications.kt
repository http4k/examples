package hexagonal.port

import arrow.core.Either

interface Notifications {
    fun notify(phoneNumber: String, message: String): Either<Exception, Int>

    companion object
}
