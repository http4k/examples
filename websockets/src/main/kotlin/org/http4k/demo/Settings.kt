package org.http4k.demo

import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.core.Credentials

val CREDENTIALS = EnvironmentKey.map(String::toCredentials, Credentials::fromCredentials)
    .defaulted("CREDENTIALS", Credentials("http4k", "http4k"))

private fun Credentials.fromCredentials() = "$user:$password"
private fun String.toCredentials() = split(":").run { Credentials(get(0), get(1)) }
