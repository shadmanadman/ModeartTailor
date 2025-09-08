package org.modeart.tailor

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import org.modeart.tailor.features.business.networking.businessRouting
import org.modeart.tailor.features.customer.networking.customerRouting
import org.modeart.tailor.jwt.ACCESS_TOKEN_EXPIRATION
import org.modeart.tailor.jwt.TokenConfig
import org.modeart.tailor.jwt.authRoute
import org.modeart.tailor.jwt.configureSecurity

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()
    val tokenConfig = TokenConfig(issuer, audience, ACCESS_TOKEN_EXPIRATION, secret, myRealm)
    configureSecurity(tokenConfig)
    routing {
        customerRouting()
        businessRouting()
        authRoute(tokenConfig)
    }
}


val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}