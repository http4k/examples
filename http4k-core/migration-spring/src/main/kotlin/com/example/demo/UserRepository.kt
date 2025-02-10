package com.example.demo

import org.springframework.stereotype.Component

data class User(val id: String, val name: String)

@Component
class UserRepository {
    fun finaAll(): List<User> = listOf(
        User("1", "Alice"),
        User("2", "Bob"),
        User("3", "Carl")
    )
}
