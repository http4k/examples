package com.example

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import java.time.Clock
import java.util.Date

fun interface Authorizer: (String) -> User?

private class TestableClaimsVerifier(
    exactMatchClaims: JWTClaimsSet,
    requiredClaims: Set<String>,
    private val clock: Clock
): DefaultJWTClaimsVerifier<SecurityContext>(exactMatchClaims, requiredClaims) {
    override fun currentTime(): Date = Date.from(clock.instant())
}

fun createHs256JwtAuthorizer(
    issuer: String,
    audience: List<String>,
    secretKey: ByteArray,
    clock: Clock
): Authorizer {
    val processor = DefaultJWTProcessor<SecurityContext>().apply {
        jwtClaimsSetVerifier = TestableClaimsVerifier(
            exactMatchClaims = JWTClaimsSet.Builder()
                .issuer(issuer)
                .audience(audience)
                .build(),
            requiredClaims = setOf("exp", "username"),
            clock = clock
        )
        jwsKeySelector = JWSVerificationKeySelector(JWSAlgorithm.HS256, ImmutableSecret(secretKey))
    }

    return Authorizer { token ->
        runCatching { SignedJWT.parse(token).let { processor.process(it, null) } }
            .map { claims -> User(id = claims.subject, name = claims.getStringClaim("username")) }
            .onFailure { println(it) }
            .getOrNull()
    }
}

