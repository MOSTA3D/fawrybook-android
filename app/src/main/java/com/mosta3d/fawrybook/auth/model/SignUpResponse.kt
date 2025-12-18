package com.mosta3d.fawrybook.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val token: String,
    val email: String,
    val username: String,
    val refreshToken: String? = null,
)
