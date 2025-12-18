package com.mosta3d.fawrybook.auth.repository

import com.mosta3d.fawrybook.BuildConfig
import com.mosta3d.fawrybook.auth.model.RefreshTokenRequest
import com.mosta3d.fawrybook.auth.model.RefreshTokenResponse
import com.mosta3d.fawrybook.shared.client.AppResponse
import com.mosta3d.fawrybook.shared.network.TokenRefresher
import com.mosta3d.fawrybook.shared.network.TokenStore
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.reflect.typeInfo
import javax.inject.Inject

class AuthTokenRefresher @Inject constructor(
    private val httpClient: HttpClient,
    private val tokenStore: TokenStore,
) : TokenRefresher {
    override suspend fun refresh(): Boolean {
        val refreshToken = tokenStore.refreshToken()?.takeIf { it.isNotBlank() } ?: return false

        return runCatching {
            val response = httpClient.post("${BuildConfig.BASE_URL}/auth/refresh") {
                expectSuccess = false
                contentType(ContentType.Application.Json)
                setBody(RefreshTokenRequest(refreshToken = refreshToken))
            }

            val appResponse: AppResponse<RefreshTokenResponse?> =
                response.body(typeInfo<AppResponse<RefreshTokenResponse?>>())

            if (!appResponse.success) return false

            val body = appResponse.data ?: return false
            tokenStore.setAccessToken(body.token)
            tokenStore.setRefreshToken(body.refreshToken)
            true
        }.getOrDefault(false)
    }
}


