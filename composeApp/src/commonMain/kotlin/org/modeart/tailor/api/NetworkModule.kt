package org.modeart.tailor.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.Logger
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.modeart.tailor.prefs.PrefsDataStore
import org.modeart.tailor.prefs.rememberDataStore
import kotlinx.serialization.json.Json
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.client.request.header

const val BASE_URL = "http://0.0.0.0:8080/"
private const val LOG_HTTP = "HTTP call"

val networkModule = module {

    single<PrefsDataStore> { rememberDataStore() }

    singleOf(::TokenRepo).bind<TokenService>()

    single {
        val tokenService: TokenService by inject()

        HttpClient(CIO) {
            defaultRequest {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("$LOG_HTTP $message")
                    }
                }
                level = LogLevel.BODY
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        tokenService.getToken()
                    }
                    refreshTokens {
                        tokenService.refreshToken()
                    }
                }
            }
            defaultRequest {
                url(BASE_URL)
            }
        }
    }
}