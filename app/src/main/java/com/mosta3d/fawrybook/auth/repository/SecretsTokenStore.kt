package com.mosta3d.fawrybook.auth.repository

import com.mosta3d.fawrybook.enums.Token
import com.mosta3d.fawrybook.shared.network.TokenStore
import javax.inject.Inject

class SecretsTokenStore @Inject constructor(
    private val secretsStore: SecretsStore
) : TokenStore {
    override suspend fun accessToken(): String? {
        return secretsStore.getItem(Token.ACCESS_TOKEN.name)
    }

    override suspend fun refreshToken(): String? {
        return secretsStore.getItem(Token.REFRESH_TOKEN.name)
    }

    override suspend fun setAccessToken(token: String) {
        secretsStore.setItem(Token.ACCESS_TOKEN.name, token)
    }

    override suspend fun setRefreshToken(token: String?) {
        secretsStore.setItem(Token.REFRESH_TOKEN.name, token ?: "")
    }

    override suspend fun clear() {
        secretsStore.setItem(Token.ACCESS_TOKEN.name, "")
        secretsStore.setItem(Token.REFRESH_TOKEN.name, "")
    }
}


