package org.modeart.tailor.jwt

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.append
import io.ktor.http.headers
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import org.modeart.tailor.features.business.di.BusinessModule
import org.modeart.tailor.model.business.AuthRequest
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.OtpData
import org.modeart.tailor.model.business.OtpRequest
import org.modeart.tailor.model.business.OtpResponse
import org.modeart.tailor.model.business.PhoneCheckRequest
import org.modeart.tailor.model.business.PhoneCheckResponse
import org.modeart.tailor.model.business.RefreshTokenRequest
import org.modeart.tailor.model.business.SmsIrRequest
import org.modeart.tailor.model.business.Tokens
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

val otpStore = ConcurrentHashMap<String, OtpData>()
const val OTP_EXPIRY_SECONDS = 300
const val SMS_IR_API_KEY = "YOUR_API_KEY"
const val SMS_IR_TEMPLATE_ID = 123456

val httpClient = HttpClient(CIO) {
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
}

fun Route.authRoute(tokenConfig: TokenConfig) {
    val repository = BusinessModule.businessDao()


    post("/register") {
        val request = call.receive<AuthRequest>()

        // 1. Verify the OTP
        val storedOtpData = otpStore[request.phoneNumber]
        if (storedOtpData == null || storedOtpData.otp != request.otp || storedOtpData.expiry < System.currentTimeMillis()) {
            return@post call.respond(
                HttpStatusCode.BadRequest,
                "Invalid or expired OTP"
            )
        }

        // 2. Clear the OTP from the store after successful verification
        otpStore.remove(request.phoneNumber)

        // Save user to database
        val insertOneResult =
            repository.insertOne(BusinessProfile(phoneNumber = request.phoneNumber))
        val userId = insertOneResult?.asObjectId()?.value?.toHexString()
            ?: return@post call.respond(HttpStatusCode.InternalServerError, "Failed to create user")

        val accessToken = JwtConfig.generateAccessToken(userId, tokenConfig)
        val refreshToken = JwtConfig.generateRefreshToken(userId, tokenConfig)

        call.respond(HttpStatusCode.OK, Tokens(accessToken, refreshToken))
    }

    post("/login") {
        val request = call.receive<AuthRequest>()
        // Validate user credentials from your database
        val user = repository.findByPhone(request.phoneNumber) ?: return@post call.respond(
            HttpStatusCode.Unauthorized,
            "Invalid credentials"
        )

        // 1. Verify the OTP
        val storedOtpData = otpStore[request.phoneNumber]
        if (storedOtpData == null || storedOtpData.otp != request.otp || storedOtpData.expiry < System.currentTimeMillis()) {
            return@post call.respond(
                HttpStatusCode.BadRequest,
                "Invalid or expired OTP"
            )
        }

        // 2. Clear the OTP from the store after successful verification
        otpStore.remove(request.phoneNumber)
        val userId = user.getObjectId("_id").toHexString()
        val accessToken = JwtConfig.generateAccessToken(userId, tokenConfig)
        val refreshToken = JwtConfig.generateRefreshToken(userId, tokenConfig)
        call.respond(HttpStatusCode.OK, Tokens(accessToken, refreshToken))
    }

    post("/refresh-token") {
        val request = call.receive<RefreshTokenRequest>()
        try {
            JwtConfig.verifyToken(request.refreshToken, tokenConfig)
        } catch (e: Exception) {
            return@post call.respond(HttpStatusCode.Unauthorized, "Invalid refresh token")
        }
        val user = repository.findByPhone(request.phoneNumber) ?: return@post call.respond(
            HttpStatusCode.Unauthorized,
            "Invalid credentials"
        )
        val userId = user.getObjectId("_id").toHexString()

        val newAccessToken = JwtConfig.generateAccessToken(userId, tokenConfig)
        val refreshToken = JwtConfig.generateRefreshToken(userId, tokenConfig)

        call.respond(
            HttpStatusCode.OK,
            Tokens(accessToken = newAccessToken, refreshToken = refreshToken)
        )
    }


    post("/check-phone-exists") {
        val request = call.receive<PhoneCheckRequest>()
        val phoneExists = repository.findByPhone(request.phoneNumber) != null
        call.respond(HttpStatusCode.OK, PhoneCheckResponse(exists = phoneExists))
    }

    post("/send-otp-test") {
        val request = call.receive<OtpRequest>()

        // 1. Generate a 5-digit code
        val otp = (10000..99999).random().toString()

        // 2. Store the OTP with an expiry
        val expiryTime = System.currentTimeMillis() + (OTP_EXPIRY_SECONDS * 1000)
        otpStore[request.phoneNumber] = OtpData(request.phoneNumber, otp, expiryTime)

        call.respond(
            HttpStatusCode.OK,
            AuthRequest(phoneNumber = request.phoneNumber, otp = otp)
        )
    }

    post("/send-otp") {
        val request = call.receive<OtpRequest>()

        // 1. Generate a 5-digit code
        val otp = (10000..99999).random().toString()

        // 2. Store the OTP with an expiry
        val expiryTime = System.currentTimeMillis() + (OTP_EXPIRY_SECONDS * 1000)
        otpStore[request.phoneNumber] = OtpData(request.phoneNumber, otp, expiryTime)

        // 3. Send a request to the SMS service
        try {
            val smsIrRequest = SmsIrRequest(
                mobile = request.phoneNumber,
                templateId = SMS_IR_TEMPLATE_ID,
                parameters = listOf(SmsIrRequest.SmsIrParameter("Code", otp))
            )

            val response: HttpResponse = httpClient.post("https://api.sms.ir/v1/send/verify") {
                headers {
                    append("x-api-key", SMS_IR_API_KEY)
                    append(HttpHeaders.ContentType, ContentType.Application.Json)
                }
                setBody(smsIrRequest)
            }

            if (response.status.isSuccess()) {
                // Handle successful response from sms.ir
                call.respond(
                    HttpStatusCode.OK,
                    OtpResponse(success = true, message = "OTP sent successfully")
                )
            } else {
                // Handle error response from sms.ir
                val errorBody = response.bodyAsText()
                call.respond(HttpStatusCode.InternalServerError, "Failed to send OTP: $errorBody")
            }
        } catch (e: Exception) {
            println("Exception sending OTP: ${e.message}")
            call.respond(
                HttpStatusCode.InternalServerError,
                "An error occurred while sending the OTP."
            )
        }
    }
}

