package org.modeart.tailor.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

class MainHttpClient() {
    operator fun invoke() =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens("your-access-token", "your-refresh-token")
                    }
                    refreshTokens { BearerTokens("new-access-token", "new-refresh-token") }
                }
            }
            defaultRequest {
                url("https://your-base-url.com/api/")
            }
        }
}