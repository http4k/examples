package env

interface Buyer {
    fun marksItemDispatched(trackingNumber: String)
}

interface Seller {
    fun receivedMessage(): String?
}
