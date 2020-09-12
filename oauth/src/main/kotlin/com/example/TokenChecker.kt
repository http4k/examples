package com.example

import org.http4k.security.AccessToken

object TokenChecker {
    fun check(accessToken: AccessToken) = accessToken.value.startsWith("ACCESS_TOKEN")
}
