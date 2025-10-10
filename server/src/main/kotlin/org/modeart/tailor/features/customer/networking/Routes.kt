package org.modeart.tailor.features.customer.networking

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
import org.modeart.tailor.features.customer.di.CustomerModule
import org.modeart.tailor.model.customer.CustomerProfile


fun Route.customerRouting() {
    val repository = CustomerModule.customerDao()
    authenticate("auth-jwt") {

        route("/customer") {

            get {
                repository.findAll()?.let { list ->
                    call.respond(list)
                } ?: call.respondText("No records found")
            }

            get("/customers") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()

                if (userId.isNullOrEmpty()) {
                    return@get call.respondText(
                        text = "Missing businessId",
                        status = HttpStatusCode.BadRequest
                    )
                }
                repository.findByBusiness(userId)?.let { list ->
                    call.respond(list)
                } ?: call.respondText("No records found for businessId $userId")
            }

            get("/{id?}") {
                val id = call.parameters["id"]
                if (id.isNullOrEmpty()) {
                    return@get call.respondText(
                        text = "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                }

                repository.findById(ObjectId(id))?.let {
                    call.respond(it)
                } ?: call.respondText("No records found for id $id")
            }

            delete("/{id?}") {
                val id = call.parameters["id"] ?: return@delete call.respondText(
                    text = "Missing customer id",
                    status = HttpStatusCode.BadRequest
                )

                val delete: Long = repository.deleteById(ObjectId(id))

                if (delete == 1L) {
                    return@delete call.respondText(
                        "customer Deleted successfully",
                        status = HttpStatusCode.OK
                    )
                }
                return@delete call.respondText(
                    "customer not found",
                    status = HttpStatusCode.NotFound
                )

            }

            patch("/{id?}") {
                val id = call.parameters["id"] ?: return@patch call.respondText(
                    text = "Missing customer id",
                    status = HttpStatusCode.BadRequest
                )

                val updated = repository.updateOne(ObjectId(id), call.receive())

                call.respondText(
                    text = if (updated == 1L) "customer updated successfully" else "customer not found",
                    status = if (updated == 1L) HttpStatusCode.OK else HttpStatusCode.NotFound
                )
            }

            post {
                val customer = call.receive<CustomerProfile>()
                val insertedId = repository.insertOne(customer)
                call.respond(HttpStatusCode.Created, "Created customer with id $insertedId")
            }
        }
    }
}
