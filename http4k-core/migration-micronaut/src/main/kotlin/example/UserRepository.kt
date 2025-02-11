package example

import io.micronaut.context.annotation.Bean
import io.micronaut.serde.annotation.Serdeable


@Serdeable.Serializable
data class User(val id: String, val name: String)

@Bean
class UserRepository {
    fun finaAll(): List<User> = listOf(
        User("1", "Alice"),
        User("2", "Bob"),
        User("3", "Carl")
    )
}
