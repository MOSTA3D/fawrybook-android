package com.mosta3d.fawrybook.shared.network

interface TokenStore {
    suspend fun accessToken(): String?
    suspend fun refreshToken(): String?

    suspend fun setAccessToken(token: String)
    suspend fun setRefreshToken(token: String?)

    suspend fun clear()
}


