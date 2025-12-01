package com.mosta3d.fawrybook.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosta3d.fawrybook.auth.event.LoginEvent
import com.mosta3d.fawrybook.auth.model.LoginRequest
import com.mosta3d.fawrybook.auth.repository.AuthRepository
import com.mosta3d.fawrybook.auth.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _loginEventFlow = MutableSharedFlow<LoginEvent>()
    val loginEventFlow = _loginEventFlow.asSharedFlow()

    fun onEmailChange(value: String) {
        _state.value =
            _state.value.copy(emailField = _state.value.emailField.copy(emailValue = value))
    }

    fun onPasswordChange(value: String) {
        _state.value =
            _state.value.copy(passwordField = _state.value.passwordField.copy(passwordValue = value))
    }

    fun togglePasswordVisibility() {
        _state.value =
            _state.value.copy(
                isPasswordVisibleField = _state.value.isPasswordVisibleField.copy(
                    isPasswordVisible = !_state.value.isPasswordVisibleField.value
                )
            )
    }

    fun login() {
        val loginRequest = _state.value.toLoginRequest()
        viewModelScope.launch {
            val response = authRepository.login(loginRequest)
            if (response.code != "0") {
                _loginEventFlow.emit(LoginEvent.Error(response.messages))
                return@launch
            }

            _loginEventFlow.emit(LoginEvent.Success(
                token = response.data?.token ?: "",
                email = response.data?.email ?: "",
                userId = response.data?.userId ?: ""
            ))
        }
    }
}