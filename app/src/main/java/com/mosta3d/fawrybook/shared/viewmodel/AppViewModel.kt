package com.mosta3d.fawrybook.shared.viewmodel

import androidx.lifecycle.ViewModel
import com.mosta3d.fawrybook.shared.model.AuthData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
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