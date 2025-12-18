package com.mosta3d.fawrybook.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosta3d.fawrybook.auth.event.LoginEvent
import com.mosta3d.fawrybook.auth.repository.AuthRepository
import com.mosta3d.fawrybook.auth.state.LoginState
import com.mosta3d.fawrybook.auth.repository.SecretsStore
import com.mosta3d.fawrybook.enums.Token
import com.mosta3d.fawrybook.shared.form.AppFieldData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val secretStore: SecretsStore,
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(LoginState())
    val stateFlow = _stateFlow.asStateFlow()

    private val _loginEventFlow = MutableSharedFlow<LoginEvent>()
    val loginEventFlow = _loginEventFlow.asSharedFlow()

    // refactor : move this to be inside appfield
    fun onEmailChange(value: AppFieldData<String>) {
        _stateFlow.value = _stateFlow.value.copy(emailField = value)
    }

    fun onPasswordChange(value: AppFieldData<String>) {
        _stateFlow.value = _stateFlow.value.copy(passwordField = value)
    }

    fun submit() {
        val loginRequest = _stateFlow.value.toLoginRequest()
        viewModelScope.launch {
            val response = authRepository.login(loginRequest)
            if (!response.success) {
                _loginEventFlow.emit(LoginEvent.Error(response.messages))
                return@launch
            }

            secretStore.setItem(Token.ACCESS_TOKEN.name, response.data?.token ?: "")
            secretStore.setItem(Token.REFRESH_TOKEN.name, response.data?.refreshToken ?: "")

            _loginEventFlow.emit(
                LoginEvent.Success(
                    token = response.data?.token ?: "",
                    email = response.data?.email ?: "",
                    userId = response.data?.username ?: ""
                )
            )
        }
    }
}