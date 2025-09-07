package org.modeart.tailor.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.serialization.Serializable
import org.modeart.tailor.audience
import org.modeart.tailor.issuer
import org.modeart.tailor.secret
import java.util.Date

object JwtConfig {
    private const val ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000L // 1 hour
    private const val REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000L // 24 hours
    private val algorithm = Algorithm.HMAC256(secret)

    fun generateAccessToken(userId: String): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
            .sign(algorithm)

    fun generateRefreshToken(userId: String): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
            .sign(algorithm)

    val verifier: JWTVerifier = JWT.require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()
}

@Serializable
data class Tokens(
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class AuthRequest(
    val phoneNumber: String,
    val otp: String
)