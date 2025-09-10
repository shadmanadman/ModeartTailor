package org.modeart.tailor.api.auth

import org.modeart.tailor.model.business.OtpRequest
import org.modeart.tailor.model.business.PhoneCheckRequest
import org.modeart.tailor.model.business.PhoneCheckResponse
import org.modeart.tailor.model.business.Tokens

interface OnBoardingService {
    suspend fun login(otpRequest: OtpRequest): Tokens
    suspend fun register(otpRequest: OtpRequest): Tokens
    suspend fun checkPhoneNumber(phoneCheckRequest: PhoneCheckRequest): PhoneCheckResponse
}