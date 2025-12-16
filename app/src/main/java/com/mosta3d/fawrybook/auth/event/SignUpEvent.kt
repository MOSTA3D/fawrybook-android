package com.mosta3d.fawrybook.auth.event

sealed class SignUpEvent {
    data class Success(
        val token: String,
        val email: String,
        val userId: String
    ) : SignUpEvent()

    data class Error(val messages: List<String>) : SignUpEvent()
}