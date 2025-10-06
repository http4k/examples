package hexagonal.domain

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.flatMap
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
            .flatMap { userPair ->
                val (sender, recipient) = userPair
                findNumberFor(recipient)
                    .flatMap { phoneNumber -> notify(sender, recipient, trackingNumber, phoneNumber) }
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


    private fun findUsers(senderId: Int, recipientId: Int): Either<DispatchResult, Pair<User, User>> {
        val sender = users.nameFor(senderId)?.let(::Right) ?: Left(UserNotFound(senderId))
        val recipient = users.nameFor(recipientId)?.let(::Right) ?: Left(UserNotFound(senderId))

        return sender.flatMap { recipient.map { recipientUser -> it to recipientUser } }
    }

    private fun findNumberFor(user: User) = phoneBook
        .numberFor(user.name)
        .mapLeft { OtherFailure("no phone number for $user") }
}

sealed class DispatchResult {
    data class UserNotFound(val id: Int) : DispatchResult()
    data class OtherFailure(val message: String) : DispatchResult()
}
