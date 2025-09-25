package org.modeart.tailor.api

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.modeart.tailor.api.auth.OnBoardingService
import org.modeart.tailor.model.business.RefreshTokenRequest
import org.modeart.tailor.prefs.PrefsDataStore
import kotlin.coroutines.cancellation.CancellationException

interface TokenService {
    suspend fun saveToken(
        accessToken: String,
        refreshToken: String
    )

    suspend fun getToken(): BearerTokens

    suspend fun refreshToken(): BearerTokens

    suspend fun isLoggedIn(): Boolean

    suspend fun logout()
}

private const val ACCESS_TOKEN = "access_token"
private const val REFRESH_TOKEN = "refresh_token"

class TokenRepo(
    private val dataStore: PrefsDataStore,
    private val onBoardingRepo: OnBoardingService
) :
    TokenService {
    override suspend fun saveToken(accessToken: String, refreshToken: String) {
        return withContext(Dispatchers.IO) {
            try {
                dataStore.edit {
                    it[stringPreferencesKey(ACCESS_TOKEN)] = accessToken
                    it[stringPreferencesKey(REFRESH_TOKEN)] = refreshToken
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }

    override suspend fun getToken(): BearerTokens {
        return withContext(Dispatchers.IO) {
            val preferences = dataStore.data.first()
            val accessToken = preferences[stringPreferencesKey(ACCESS_TOKEN)] ?: ""
            val refreshToken = preferences[stringPreferencesKey(REFRESH_TOKEN)] ?: ""
            BearerTokens(accessToken, refreshToken)
        }
    }

    override suspend fun refreshToken(): BearerTokens {
        return withContext(Dispatchers.IO) {
            val preferences = dataStore.data.first()
            val refreshToken = preferences[stringPreferencesKey(REFRESH_TOKEN)] ?: ""


            val tokens = onBoardingRepo.refreshToken(RefreshTokenRequest(refreshToken, ""))
            when (tokens) {
                is ApiResult.Error -> {
                    BearerTokens("", "")
                    throw Exception(tokens.message)
                }

                is ApiResult.Success -> {
                    saveToken(tokens.data.accessToken, tokens.data.refreshToken)
                    BearerTokens(tokens.data.accessToken, tokens.data.refreshToken)
                }
            }

        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return withContext(Dispatchers.IO) {
            val preferences = dataStore.data.first()
            preferences[stringPreferencesKey(ACCESS_TOKEN)] != null
        }
    }

    override suspend fun logout() {
        return withContext(Dispatchers.IO) {
            try {
                dataStore.edit {
                    it.clear()
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }
}