package org.modeart.tailor.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun Application.configureSecurity(tokenConfig: TokenConfig) {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = tokenConfig.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(tokenConfig.secret))
                    .withAudience(tokenConfig.audience)
                    .withIssuer(tokenConfig.issuer)
                    .build()
            )
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()
                if (credential.payload.audience.contains(tokenConfig.audience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}