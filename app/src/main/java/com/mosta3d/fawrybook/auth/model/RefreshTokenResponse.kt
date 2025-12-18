package com.mosta3d.fawrybook.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val token: String,
    val refreshToken: String? = null,
)


