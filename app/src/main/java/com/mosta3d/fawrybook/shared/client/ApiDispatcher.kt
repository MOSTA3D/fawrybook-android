package com.mosta3d.fawrybook.shared.client

import io.ktor.client.statement.HttpResponse

interface ApiDispatcher {
    suspend fun dispatch(
        httpMethod: HttpMethodEnum,
        data: Any?,
        segments: Array<String>,
        options: Options?,
    ): Result<HttpResponse>
}