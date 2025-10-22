package org.modeart.tailor.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date

const val ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000L // 1 hour
const val REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000L // 24 hours
object JwtConfig {

    fun generateAccessToken(userId: String, tokenConfig: TokenConfig): String =
        JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withClaim("userId", userId)
            .sign(Algorithm.HMAC256(tokenConfig.secret))

    fun generateRefreshToken(userId: String, tokenConfig: TokenConfig): String =
        JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withClaim("userId", userId)
            .sign(Algorithm.HMAC256(tokenConfig.secret))

    fun verifyToken(token: String, tokenConfig: TokenConfig): DecodedJWT? =
        JWT.require(Algorithm.HMAC256(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .build()
            .verify(token)
}
