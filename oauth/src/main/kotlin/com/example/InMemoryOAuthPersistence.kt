package com.example

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.FORBIDDEN
import org.http4k.core.Uri
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.core.cookie.invalidateCookie
import org.http4k.security.AccessToken
import org.http4k.security.CrossSiteRequestForgeryToken
import org.http4k.security.Nonce
import org.http4k.security.OAuthCallbackError
import org.http4k.security.OAuthPersistence
import org.http4k.security.PkceChallengeAndVerifier
import org.http4k.security.openid.IdToken
import java.time.Clock
import java.util.UUID

/**
 * This persistence handles both Bearer-token (API) and cookie-swapped access token (standard OAuth-web) flows.
 */
class InMemoryOAuthPersistence(private val clock: Clock) : OAuthPersistence {
    private val csrfName = "exampleServerCsrf"
    private val clientAuthCookie = "exampleServerAuth"
    private val originalUriName = "exampleServerUri"
    private val cookieSwappableTokens = mutableMapOf<String, AccessToken>()

    override fun retrieveCsrf(request: Request) = request.cookie(csrfName)?.value?.let(::CrossSiteRequestForgeryToken)

    override fun retrieveNonce(request: Request): Nonce? = null

    override fun retrieveOriginalUri(request: Request): Uri? =
        request.cookie(originalUriName)?.value?.let(Uri::of)

    override fun retrievePkce(request: Request) = null

    override fun retrieveToken(request: Request) = (tryBearerToken(request)
        ?: tryCookieToken(request))
        ?.takeIf { it.value.startsWith("ACCESS_TOKEN") }

    override fun assignCsrf(redirect: Response, csrf: CrossSiteRequestForgeryToken) =
        redirect.cookie(expiring(csrfName, csrf.value))

    override fun assignNonce(redirect: Response, nonce: Nonce): Response = redirect
    override fun assignOriginalUri(redirect: Response, originalUri: Uri): Response =
        redirect.cookie(expiring(originalUriName, originalUri.toString()))

    override fun assignPkce(redirect: Response, pkce: PkceChallengeAndVerifier): Response {
        TODO("Not yet implemented")
    }

    override fun assignToken(request: Request, redirect: Response, accessToken: AccessToken, idToken: IdToken?) =
        UUID.randomUUID().let {
            cookieSwappableTokens[it.toString()] = accessToken
            redirect.cookie(expiring(clientAuthCookie, it.toString()))
                .invalidateCookie(csrfName)
                .invalidateCookie(originalUriName)
        }

    override fun authFailureResponse(reason: OAuthCallbackError) = Response(FORBIDDEN)
        .invalidateCookie(csrfName)
        .invalidateCookie(originalUriName)
        .invalidateCookie(clientAuthCookie)

    private fun tryCookieToken(request: Request) =
        request.cookie(clientAuthCookie)?.value?.let { cookieSwappableTokens[it] }

    private fun tryBearerToken(request: Request) = request.header("Authorization")
        ?.removePrefix("Bearer ")
        ?.let { AccessToken(it) }

    private fun expiring(name: String, value: String) = Cookie(
        name, value,
        path = "/",
        expires = clock.instant().plusSeconds(900)
    )
}
