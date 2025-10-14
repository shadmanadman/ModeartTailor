package org.modeart.tailor.features.business.networking

import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.bson.types.ObjectId
import org.modeart.tailor.HOST
import org.modeart.tailor.features.business.di.BusinessModule
import org.modeart.tailor.features.customer.di.CustomerModule
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.ImageUploadResponse
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
                        val name = "${UUID.randomUUID()}.png"
                        val file = File("uploads/$name")
                        file.parentFile.mkdirs()
                        part.streamProvider().use { it.copyTo(file.outputStream()) }
                        imageUrl = "http://localhost:8080/uploads/$name"
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