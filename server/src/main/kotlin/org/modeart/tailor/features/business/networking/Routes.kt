package org.modeart.tailor.features.business.networking

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.append
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.headers
import io.ktor.http.isSuccess
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.utils.io.jvm.javaio.toInputStream
import org.modeart.tailor.BASE_URL
import org.modeart.tailor.HOST
import org.modeart.tailor.features.business.di.BusinessModule
import org.modeart.tailor.jwt.httpClient
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.BuyPlanRequest
import org.modeart.tailor.model.business.ImageUploadResponse
import org.modeart.tailor.model.business.PaymentAddressRequest
import org.modeart.tailor.model.business.PaymentAddressResponse
import org.modeart.tailor.model.business.PlanType
import org.modeart.tailor.model.business.ZarinpalResponse
import java.io.File
import java.util.UUID

fun Route.businessRouting() {
    val repository = BusinessModule.businessDao()

    authenticate("auth-jwt") {
        route("/business") {

            get {
                repository.findAll()?.let { list ->
                    call.respond(list)
                } ?: call.respondText("No records found")
            }

            get("/profile") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()

                println("USER_ID is: $userId")
                if (userId.isNullOrEmpty()) {
                    return@get call.respondText(
                        text = "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                }

                repository.findById(userId)?.let {
                    call.respond(it)
                } ?: call.respondText("No records found for id $userId")
            }



            delete("/remove") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()

                println("USER_ID is: $userId")
                if (userId.isNullOrEmpty()) {
                    return@delete call.respondText(
                        text = "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                }

                val delete: Long = repository.deleteById(userId)

                if (delete == 1L) {
                    return@delete call.respondText(
                        "business Deleted successfully",
                        status = HttpStatusCode.OK
                    )
                }
                return@delete call.respondText(
                    "business not found",
                    status = HttpStatusCode.NotFound
                )

            }
            patch("/update-profile") {
                val request = call.receive<BusinessProfile>()
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                if (userId.isNullOrEmpty()) {
                    return@patch call.respondText(
                        text = "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                }
                val updated = repository.updateOne(objectId = userId, businessProfile = request)
                call.respondText(
                    text = if (updated == 1L) "business updated successfully" else "business not found",
                    status = if (updated == 1L) HttpStatusCode.OK else HttpStatusCode.NotFound
                )
            }


            post {
                val business = call.receive<BusinessProfile>()
                val insertedId = repository.insertOne(business)
                call.respond(HttpStatusCode.Created, "Created business with id $insertedId")
            }

            post("/note") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                if (userId.isNullOrEmpty()) {
                    return@post call.respondText(
                        text = "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                }
                var note = call.receive<BusinessProfile.Notes>()
                note = note.copy(id = UUID.randomUUID().toString())
                val insertedId = repository.insertNote(userId, note)
                call.respond(HttpStatusCode.Created, "Created note with id $insertedId")
            }

            get("/notes") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                if (userId.isNullOrEmpty()) {
                    return@get call.respondText(
                        text = "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                }
                repository.getAllNotes(userId)?.let { notes ->
                    call.respond(notes)
                } ?: call.respondText(
                    "Business not found for id $userId",
                    status = HttpStatusCode.NotFound
                )
            }



            delete("/note/{noteId?}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()
                if (userId.isNullOrEmpty()) {
                    return@delete call.respondText(
                        text = "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                }
                val noteId = call.parameters["noteId"] ?: return@delete call.respondText(
                    text = "Missing noteId",
                    status = HttpStatusCode.BadRequest
                )
                val deletedCount = repository.deleteNote(userId, noteId)
                if (deletedCount == 1L) {
                    call.respondText("Note deleted successfully", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Note not found", status = HttpStatusCode.NotFound)
                }
            }


            get("payment/address"){
                val request = call.receive<BuyPlanRequest>()
                val amount = if (request.planType == PlanType.MONTHLY) 1000 else 250000
                try {
                    val paymentAddressRequest = PaymentAddressRequest(
                        amount = amount,
                        description = "Buy ${request.planType} Plan",
                        merchantId = "",
                        callbackUrl = "http://$BASE_URL:8080/payment/callback"
                    )

                    val response: HttpResponse = httpClient.post("https://payment.zarinpal.com/pg/v4/payment/request.json") {
                        headers {
                            append(HttpHeaders.ContentType, ContentType.Application.Json)
                        }
                        setBody(paymentAddressRequest)
                    }

                    if (response.status.isSuccess()) {
                        val paymentAddressResponse: ZarinpalResponse = response.body()
                        val paymentAddress = "https://payment.zarinpal.com/pg/StartPay/result['data'][${paymentAddressResponse.authority}]"
                        // Handle successful response from zarinpal
                        call.respond(
                            HttpStatusCode.OK,
                            call.respond(PaymentAddressResponse(url = paymentAddress))
                        )
                    } else {
                        // Handle error response from zarinpal
                        val errorBody = response.bodyAsText()
                        call.respond(HttpStatusCode.InternalServerError, "Failed to get payment address: $errorBody")
                    }
                } catch (e: Exception) {
                    println("Exception sending OTP: ${e.message}")
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        "An error occurred while sending the OTP."
                    )
                }
            }

            get("/payment/callback") {
                val queryParams = call.request.queryParameters
                val status = queryParams["Status"]
                val authority = queryParams["Authority"]

                // 2. Determine success or failure and set internal variables
                val internalStatus: String
                val internalAuthority: String = authority ?: "unknown"

                if (status == "OK") {
                    // IMPORTANT: The backend 'verify' method should only be called here.
                    // callVerificationAPI(internalAuthority) // <-- Your API call goes here
                    internalStatus = "success"
                } else {
                    // Status is "NOK" (Failed or Cancelled)
                    internalStatus = "failure"
                }

                // 3. Construct the Deep Link URL with clean, English keys for the app
                val deepLinkUrl = "modeart://payment_result?authority=$internalAuthority&status=$internalStatus"

                call.respondRedirect(deepLinkUrl, permanent = false)
            }
        }
    }




}

fun Route.configureUpload() {
    route("/upload") {
        post("/image") {
            val multipart = call.receiveMultipart()
            var imageUrl: String? = null

            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        val fileName = part.save(Constants.UPLOAD_PATH)
                        imageUrl = "${Constants.EXTERNAL_UPLOAD_PATH}/$fileName"
                    }

                    else -> Unit
                }
                part.dispose()
            }

            if (imageUrl != null)
                call.respond(HttpStatusCode.OK, ImageUploadResponse(url = imageUrl))
            else
                call.respond(HttpStatusCode.BadRequest, "No file uploaded")
        }
    }
}

object Constants {
    const val STATIC_ROOT = "static/"
    const val UPLOADS_DIRECTORY = "pictures/"
    const val UPLOAD_PATH = "$STATIC_ROOT/$UPLOADS_DIRECTORY"
    const val EXTERNAL_UPLOAD_PATH = "/images"
}

fun PartData.FileItem.save(path: String): String {
    val fileBytes = provider().toInputStream().readBytes()
    val fileExtension = originalFileName?.takeLastWhile { it != '.' }
    val fileName = UUID.randomUUID().toString() + "." + fileExtension
    val folder = File(path)
    if (!folder.parentFile.exists()) {
        folder.parentFile.mkdirs()
    }
    folder.mkdir()
    File("$path$fileName").writeBytes(fileBytes)
    return fileName
}