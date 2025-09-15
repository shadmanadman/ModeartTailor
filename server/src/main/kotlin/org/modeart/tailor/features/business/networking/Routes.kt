package org.modeart.tailor.features.business.networking

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.bson.types.ObjectId
import org.modeart.tailor.features.business.di.BusinessModule
import org.modeart.tailor.model.business.BusinessProfile

fun Route.businessRouting() {
    val repository = BusinessModule.businessDao()

    route("/all-business") {

        get {
            repository.findAll()?.let { list ->
                call.respond(list)
            } ?: call.respondText("No records found")
        }

        authenticate {
            get("/business") {
                // Get the principal from the call object
                val principal = call.principal<JWTPrincipal>()

                // Check if the principal exists and get the userId
                val userId = principal?.payload?.getClaim("userId")?.asString()

                if (userId.isNullOrEmpty()) {
                    return@get call.respondText(
                        text = "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                }

                repository.findById(ObjectId(userId))?.let {
                    call.respond(it)
                } ?: call.respondText("No records found for id $userId")
            }
        }


        delete("/{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                text = "Missing business id",
                status = HttpStatusCode.BadRequest
            )

            val delete: Long = repository.deleteById(ObjectId(id))

            if (delete == 1L) {
                return@delete call.respondText(
                    "business Deleted successfully",
                    status = HttpStatusCode.OK
                )
            }
            return@delete call.respondText("business not found", status = HttpStatusCode.NotFound)

        }

        patch("/{id?}") {
            val id = call.parameters["id"] ?: return@patch call.respondText(
                text = "Missing business id",
                status = HttpStatusCode.BadRequest
            )

            val updated = repository.updateOne(ObjectId(id), call.receive())

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

        post("/{businessId?}/note") {
            val businessId = call.parameters["businessId"] ?: return@post call.respondText(
                text = "Missing businessId",
                status = HttpStatusCode.BadRequest
            )
            val note = call.receive<BusinessProfile.Notes>()
            val insertedId = repository.insertNote(businessId, note)
            call.respond(HttpStatusCode.Created, "Created note with id $insertedId")
        }

        get("/{businessId?}/notes") {
            val businessId = call.parameters["businessId"] ?: return@get call.respondText(
                text = "Missing businessId",
                status = HttpStatusCode.BadRequest
            )
            repository.getAllNotes(businessId)?.let { notes ->
                if (notes.isNotEmpty()) {
                    call.respond(notes)
                } else {
                    call.respondText(
                        "No notes found for businessId $businessId",
                        status = HttpStatusCode.NotFound
                    )
                }
            } ?: call.respondText(
                "Business not found for id $businessId",
                status = HttpStatusCode.NotFound
            )
        }

        

        delete("/{businessId?}/note/{noteId?}") {
            val businessId = call.parameters["businessId"] ?: return@delete call.respondText(
                text = "Missing businessId",
                status = HttpStatusCode.BadRequest
            )
            val noteId = call.parameters["noteId"] ?: return@delete call.respondText(
                text = "Missing noteId",
                status = HttpStatusCode.BadRequest
            )
            val deletedCount = repository.deleteNote(businessId, noteId)
            if (deletedCount == 1L) {
                call.respondText("Note deleted successfully", status = HttpStatusCode.OK)
            } else {
                call.respondText("Note not found", status = HttpStatusCode.NotFound)
            }
        }
    }
}