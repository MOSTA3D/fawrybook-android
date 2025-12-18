package com.mosta3d.fawrybook.shared.client

import io.ktor.client.call.body
import com.mosta3d.fawrybook.shared.network.NetworkEvent
import com.mosta3d.fawrybook.shared.network.NetworkEventBus
import io.ktor.util.reflect.typeInfo
import javax.inject.Inject

class FBApiClient @Inject constructor(
    private val apiDispatcher: ApiDispatcher,
    private val networkEventBus: NetworkEventBus,
) {
    private suspend fun <R> request(
        method: HttpMethodEnum,
        segments: Array<String>,
        body: Any? = null,
        options: Options? = null,
    ): AppResponse<R> {
        val responseResult = apiDispatcher.dispatch(
            httpMethod = method,
            segments = segments,
            data = body,
            options = options
        )

        if (!responseResult.isSuccess) {
            return AppResponse(
                data = null,
                code = "1000",
                messages = listOfNotNull(responseResult.exceptionOrNull()?.message)
                    .ifEmpty { listOf("Network error") }
            )
        }

        val appResponse = responseResult.getOrThrow()
            .body<AppResponse<R>>(options?.responseType ?: typeInfo<AppResponse<R>>())

        when (appResponse.code) {
            "1001" ->
                // todo: re-invoke the request
                networkEventBus.tryEmit(NetworkEvent.AuthExpired(code = "1001"))
        }

        return appResponse
    }

    suspend fun <T, R> post(
        body: T,
        segments: Array<String>,
        options: Options? = null
    ): AppResponse<R> {
        return request(
            method = HttpMethodEnum.POST,
            segments = segments,
            body = body,
            options = options,
        )
    }
}