package com.mosta3d.fawrybook.shared.state

import com.mosta3d.fawrybook.shared.model.AuthData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppState {
    private val _auth = MutableStateFlow(AuthData())
    val auth = _auth.asStateFlow()

    fun authenticate(userId: String, email: String, token: String) {
        _auth.value = AuthData(
            isLoggedIn = true,
            email = email,
            userId = userId,
            token = token
        )
    }
}