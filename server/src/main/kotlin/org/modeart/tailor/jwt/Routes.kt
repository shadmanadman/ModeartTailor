package org.modeart.tailor.jwt

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.modeart.tailor.features.business.di.BusinessModule
import java.util.UUID

fun Route.authRoute() {
    BusinessModule.businessDao()

    post("/register") {
        call.receive<AuthRequest>()
        // In a real app, hash the password before saving
        val userId = UUID.randomUUID().toString() // Simulate user creation
        // Save user to database

        val accessToken = JwtConfig.generateAccessToken(userId)
        val refreshToken = JwtConfig.generateRefreshToken(userId)

        call.respond(HttpStatusCode.OK, Tokens(accessToken, refreshToken))
    }

    post("/login") {
        call.receive<AuthRequest>()
        // Validate user credentials from your database
        // val user = userService.findByUsernameAndPassword(authRequest.username, authRequest.passwordHash)

        // Simulating a successful login
        val userId = "some-user-id"

        if (userId != null) {
            val accessToken = JwtConfig.generateAccessToken(userId)
            val refreshToken = JwtConfig.generateRefreshToken(userId)
            call.respond(HttpStatusCode.OK, Tokens(accessToken, refreshToken))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }
}