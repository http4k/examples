package hexagonal.domain

import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import hexagonal.domain.DispatchResult.Ok

class MarketHub(
    private val phoneBook: PhoneBook,
    private val users: Users,
    private val notifications: Notifications
) {
    fun markDispatched(senderId: Int, recipientId: Int, trackingNumber: String): DispatchResult {
        val sender = users.nameFor(senderId)
            ?: return DispatchResult.UserNotFound(senderId)
        val recipient = users.nameFor(recipientId)
            ?: return DispatchResult.UserNotFound(recipientId)

        return phoneBook.numberFor(recipient.name)

            .map { phoneNumber ->
                notifications.notify(
                    phoneNumber, """Hi ${recipient.name},
                |I have sent your item.
                |The tracking number is ${trackingNumber}!
                |Regards,
                |${sender.name}""".trimMargin()
                )
            }
            .map { Ok }
            .recover {
                DispatchResult.OtherFailure("no number for $recipient")
            }
    }
}

sealed class DispatchResult {
    object Ok : DispatchResult()
    data class UserNotFound(val id: Int) : DispatchResult()
    data class OtherFailure(val message: String) : DispatchResult()
}
