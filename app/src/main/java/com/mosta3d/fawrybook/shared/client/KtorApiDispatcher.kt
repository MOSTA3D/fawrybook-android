package com.mosta3d.fawrybook.shared.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import javax.inject.Inject

class KtorApiDispatcher @Inject constructor(
    private val baseUrl: String,
    private val client: HttpClient
) : ApiDispatcher {
    override suspend fun dispatch(
        httpMethod: HttpMethodEnum,
        data: Any?,
        segments: Array<String>,
        options: Options?,
    ): Result<HttpResponse> {
        return kotlin.runCatching {
            client.request("${baseUrl}/${segments.joinToString("/")}") {
                expectSuccess = false
                method = HttpMethod.parse(httpMethod.value)
                setBody(data)
                contentType(ContentType.Application.Json)
            }
        }
    }
}