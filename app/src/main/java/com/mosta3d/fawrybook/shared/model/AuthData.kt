package com.mosta3d.fawrybook.shared.model

data class AuthData(
    val email: String? = null,
    val userId: String? = null,
    val isLoggedIn: Boolean = false,
    val token: String? = null,
)
