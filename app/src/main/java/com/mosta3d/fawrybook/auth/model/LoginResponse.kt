package com.mosta3d.fawrybook.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val email: String,
    val userId: String,
    val refreshToken: String? = null,
)
