package org.modeart.tailor.api.auth

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.safeRequest
import org.modeart.tailor.model.business.AuthRequest
import org.modeart.tailor.model.business.OtpRequest
import org.modeart.tailor.model.business.PhoneCheckRequest
import org.modeart.tailor.model.business.PhoneCheckResponse
import org.modeart.tailor.model.business.RefreshTokenRequest
import org.modeart.tailor.model.business.Tokens

class OnBoardingRepo(private val client: HttpClient) : OnBoardingService {
    override suspend fun login(authRequest: AuthRequest): ApiResult<Tokens> = safeRequest {
        client.post(urlString = "login") {
            setBody(authRequest)
        }
    }


    override suspend fun register(authRequest: AuthRequest): ApiResult<Tokens> =
        safeRequest {
            client.post(urlString = "register") {
                setBody(authRequest)
            }
        }

    override suspend fun checkPhoneNumber(phoneCheckRequest: PhoneCheckRequest): ApiResult<PhoneCheckResponse> =
        safeRequest {
            client.post(urlString = "check-phone-exists") {
                setBody(phoneCheckRequest)
            }
        }

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): ApiResult<Tokens> =
        safeRequest {
            client.post(urlString = "refresh-token") {
                setBody(refreshTokenRequest)
            }
        }

    override suspend fun sendOtp(phoneNumber: String): ApiResult<Unit> =
        safeRequest {
            client.post(urlString = "send-otp") {
                setBody(OtpRequest(phoneNumber))
            }
        }

    override suspend fun sendOtpTest(phoneNumber: String): ApiResult<AuthRequest> = safeRequest {
        client.post(urlString = "send-otp-test") {
            setBody(OtpRequest(phoneNumber))
        }
    }
}