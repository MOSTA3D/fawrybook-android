package com.mosta3d.fawrybook.auth.model

data class SignUpResponse(
    val token: String,
    val email: String,
    val userId: String
)
