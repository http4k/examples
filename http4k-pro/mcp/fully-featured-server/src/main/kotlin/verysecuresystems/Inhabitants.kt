package verysecuresystems

/**
 * Simple, in-memory persistence of who is currently inside the building
 */
class Inhabitants : Iterable<Username> {
    private val currentUsers = mutableListOf<Username>()

    fun add(user: Username) =
        when (user) {
            in currentUsers -> false
            else -> {
                currentUsers += user
                true
            }
        }

    fun remove(user: Username) =
        when (user) {
            in currentUsers -> {
                currentUsers -= user
                true
            }

            else -> false
        }

    override fun iterator(): Iterator<Username> = currentUsers.iterator()
}
