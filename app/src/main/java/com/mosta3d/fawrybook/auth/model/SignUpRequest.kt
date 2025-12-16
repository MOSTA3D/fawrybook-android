package com.mosta3d.fawrybook.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String
)
