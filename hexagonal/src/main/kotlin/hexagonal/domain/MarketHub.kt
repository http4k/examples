package hexagonal.domain

import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover

class MarketHub(
    private val phoneBook: PhoneBook,
    private val users: Users,
    private val notifications: Notifications
) {
    fun markDispatched(senderId: Int, recipientId: Int, trackingNumber: String): DispatchResult {
        val recipient = users.nameFor(recipientId)
            ?: return DispatchResult.UserNotFound(recipientId)
        val sender = users.nameFor(senderId)
            ?: return DispatchResult.UserNotFound(senderId)

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
            .map { DispatchResult.Ok }
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
