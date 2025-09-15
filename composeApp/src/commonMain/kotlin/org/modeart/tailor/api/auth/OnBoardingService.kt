package org.modeart.tailor.api.auth

import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.model.business.AuthRequest
import org.modeart.tailor.model.business.PhoneCheckRequest
import org.modeart.tailor.model.business.PhoneCheckResponse
import org.modeart.tailor.model.business.RefreshTokenRequest
import org.modeart.tailor.model.business.Tokens

interface OnBoardingService {
    suspend fun login(authRequest: AuthRequest): ApiResult<Tokens>
    suspend fun register(authRequest: AuthRequest): ApiResult<Tokens>
    suspend fun checkPhoneNumber(phoneCheckRequest: PhoneCheckRequest): ApiResult<PhoneCheckResponse>

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): ApiResult<Tokens>

    suspend fun sendOtp(phoneNumber: String): ApiResult<Unit>
}