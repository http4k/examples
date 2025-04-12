package datastar.fragments

import datastar.User
import org.http4k.template.ViewModel

data class ListUsers(val users: List<User.Saved>) : ViewModel