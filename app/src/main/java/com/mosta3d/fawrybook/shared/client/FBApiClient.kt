package com.mosta3d.fawrybook.shared.client

import io.ktor.client.call.body
import io.ktor.util.reflect.typeInfo

class FBApiClient(private val apiDispatcher: ApiDispatcher) {
    suspend fun <T, R> post(
        body: T,
        segments: Array<String>,
        options: Options? = null
    ): AppResponse<R> {
        val responseResult = apiDispatcher.dispatch(
            httpMethod = HttpMethodEnum.POST,
            segments = segments,
            data = body,
            options = options
        )
        return if (responseResult.isSuccess) responseResult.getOrThrow()
            .body(options?.responseType ?: typeInfo<Any>()) else AppResponse(
            data = null,
            code = "1000",
            messages = listOf(responseResult.exceptionOrNull()?.message!!)
        )
    }
}