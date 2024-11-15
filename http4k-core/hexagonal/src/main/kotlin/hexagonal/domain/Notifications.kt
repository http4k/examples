package hexagonal.domain

import dev.forkhandles.result4k.Result4k

interface Notifications {
    fun notify(phoneNumber: String, message: String): Result4k<Int, Exception>

    companion object
}
