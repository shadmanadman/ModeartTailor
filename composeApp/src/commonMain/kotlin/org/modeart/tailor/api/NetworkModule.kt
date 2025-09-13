package org.modeart.tailor.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.modeart.tailor.prefs.PrefsDataStore
import org.modeart.tailor.prefs.rememberDataStore

val networkModule = module {

    single<PrefsDataStore> { rememberDataStore() }

    singleOf(::TokenRepo).bind<TokenService>()

    single {
        val tokenService: TokenService by inject()

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
                        tokenService.getToken()
                    }
                    refreshTokens {
                        tokenService.refreshToken()
                    }
                }
            }
            defaultRequest {
                url("https://your-base-url.com/api/")
            }
        }
    }
}