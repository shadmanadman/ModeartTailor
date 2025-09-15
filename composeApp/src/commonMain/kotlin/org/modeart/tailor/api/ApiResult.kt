package org.modeart.tailor.api

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
}

suspend inline fun <reified T> safeRequest(block: () -> HttpResponse): ApiResult<T> {
    return try {
        val response = block()
        if (response.status.isSuccess()) {
            ApiResult.Success(response.body())
        } else {
            ApiResult.Error(response.bodyAsText(), response.status.value)
        }
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Unknown error")
    }
}