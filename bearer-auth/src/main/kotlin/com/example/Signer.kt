package com.example

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.time.Clock
import java.time.Duration
import java.util.Date

fun interface Signer: (User) -> String

fun createHs256JwtSigner(
    issuer: String,
    audience: List<String>,
    secretKey: ByteArray,
    clock: Clock,
    duration: Duration = Duration.ofHours(1)
): Signer {
    val signer = MACSigner(secretKey)

    val header = JWSHeader.Builder(JWSAlgorithm.HS256)
        .type(JOSEObjectType.JWT)
        .build()

    return Signer { user ->
        val payload = JWTClaimsSet.Builder()
            .issuer(issuer)
            .audience(audience)
            .subject(user.id)
            .expirationTime(Date.from(clock.instant() + duration))
            .claim("username", user.name)
            .build()

        SignedJWT(header, payload)
            .apply { sign(signer) }
            .serialize()
    }
}
