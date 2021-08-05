package hexagonal.test.env

interface Environment {
    val buyer: Buyer
    val seller: Seller
}
