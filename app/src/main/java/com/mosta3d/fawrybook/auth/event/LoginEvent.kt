package com.mosta3d.fawrybook.auth.event

sealed class LoginEvent {
    data class Success(
        val token: String,
        val email: String,
        val userId: String
    ) : LoginEvent()

    data class Error(val messages: List<String>) : LoginEvent()
}
