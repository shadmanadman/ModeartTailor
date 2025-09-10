package org.modeart.tailor.api.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.modeart.tailor.model.business.OtpRequest
import org.modeart.tailor.model.business.PhoneCheckRequest
import org.modeart.tailor.model.business.PhoneCheckResponse
import org.modeart.tailor.model.business.Tokens

class OnBoardingRepo(private val client: HttpClient) : OnBoardingService {
    override suspend fun login(otpRequest: OtpRequest): Tokens =
        client.post(urlString = "login") {
            setBody(otpRequest)
        }.body()

    override suspend fun register(otpRequest: OtpRequest): Tokens =
        client.post(urlString = "register") {
            setBody(otpRequest)
        }.body()

    override suspend fun checkPhoneNumber(phoneCheckRequest: PhoneCheckRequest): PhoneCheckResponse =
        client.post(urlString = "check-phone-exists") {
            setBody(phoneCheckRequest)
        }.body()

}