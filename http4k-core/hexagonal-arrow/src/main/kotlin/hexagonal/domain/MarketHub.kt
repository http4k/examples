package hexagonal.domain

import arrow.core.Either.Companion.fromNullable
import arrow.core.flatMap
import arrow.core.sequence
import hexagonal.domain.DispatchResult.OtherFailure
import hexagonal.domain.DispatchResult.UserNotFound
import hexagonal.port.Notifications
import hexagonal.port.PhoneBook
import hexagonal.port.User
import hexagonal.port.Users

class MarketHub(
    private val phoneBook: PhoneBook,
    private val users: Users,
    private val notifications: Notifications
) {
    fun markDispatched(senderId: Int, recipientId: Int, trackingNumber: String) =
        findUsers(senderId, recipientId)
            .flatMap { (sender: User, recipient: User) ->
                findNumberFor(recipient)
                    .flatMap { notify(sender, recipient, trackingNumber, it) }
            }

    private fun notify(
        sender: User,
        recipient: User,
        trackingNumber: String,
        phoneNumber: String
    ) = notifications.notify(
        phoneNumber,
        """Hi ${recipient.name},
            |I have sent your item.
            |The tracking number is ${trackingNumber}!
            |Regards,
            |${sender.name}""".trimMargin()
    ).mapLeft { OtherFailure("notification fail") }


    private fun findUsers(senderId: Int, recipientId: Int) = listOf(
        fromNullable(users.nameFor(senderId)).mapLeft { UserNotFound(senderId) },
        fromNullable(users.nameFor(recipientId)).mapLeft { UserNotFound(recipientId) }
    ).sequence().map { it[0] to it[1] }

    private fun findNumberFor(user: User) = phoneBook
        .numberFor(user.name)
        .mapLeft { OtherFailure("no phone number for $user") }
}

sealed class DispatchResult {
    data class UserNotFound(val id: Int) : DispatchResult()
    data class OtherFailure(val message: String) : DispatchResult()
}
