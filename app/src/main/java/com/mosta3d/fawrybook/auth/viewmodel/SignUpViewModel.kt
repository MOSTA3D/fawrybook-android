package com.mosta3d.fawrybook.auth.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosta3d.fawrybook.R
import com.mosta3d.fawrybook.auth.event.SignUpEvent
import com.mosta3d.fawrybook.auth.repository.AuthRepository
import com.mosta3d.fawrybook.auth.state.SignUpState
import com.mosta3d.fawrybook.shared.form.AppFieldData
import com.mosta3d.fawrybook.shared.form.validator.AppValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    companion object {
        val passwordRegex =
            Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
    }

    private val _state = MutableStateFlow(getSignUpState())
    val state = _state.asStateFlow()

    private val _signUpEventFlow = MutableSharedFlow<SignUpEvent>()
    val signUpEventFlow = _signUpEventFlow.asSharedFlow()

    fun onUsernameChange(fieldData: AppFieldData<String>) {
        _state.value = _state.value.copy(usernameFieldData = fieldData)
    }

    fun onEmailChange(fieldData: AppFieldData<String>) {
        _state.value = _state.value.copy(emailFieldData = fieldData)
    }

    fun onPasswordChange(fieldData: AppFieldData<String>) {
        _state.value = _state.value.copy(passwordFieldData = fieldData)
    }

    fun onConfirmPasswordChange(fieldData: AppFieldData<String>) {
        _state.value = _state.value.copy(confirmPasswordFieldData = fieldData)
    }

    fun submit() {
        viewModelScope.launch {
            val response = authRepository.signUp(_state.value.toRequestPayload())
            if (!response.success) {
                _signUpEventFlow.emit(SignUpEvent.Error(response.messages))
                return@launch
            }

            _signUpEventFlow.emit(
                SignUpEvent.Success(
                    token = response.data?.token ?: "",
                    email = response.data?.email ?: "",
                    userId = response.data?.userId ?: ""
                )
            )
        }
    }

    private fun getSignUpState(): SignUpState {
        val signUpState = SignUpState(
            usernameFieldData = AppFieldData(
                value = "",
                validators = listOf(
                    AppValidator(R.string.username_required) { it.isNotEmpty() },
                )
            ),
            emailFieldData = AppFieldData(
                value = "",
                validators = listOf(
                    AppValidator(R.string.email_required) { it.isNotEmpty() },
                    AppValidator(R.string.email_invalid) {
                        Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    }
                )),
            passwordFieldData = AppFieldData(
                value = "",
                validators = listOf(
                    AppValidator(R.string.password_required) { it.isNotEmpty() },
                    AppValidator(R.string.password_invalid) { passwordRegex.matches(it) }
                )
            ),
            confirmPasswordFieldData = AppFieldData(
                value = "",
                validators = listOf(AppValidator(R.string.confirm_password_required) { it.isNotEmpty() })
            ),
        )

        return signUpState.copy(
            confirmPasswordFieldData = signUpState.confirmPasswordFieldData.copy(
                validators = listOf(
                    AppValidator(R.string.confirm_password_required) { it.isNotEmpty() },
                    AppValidator(R.string.passwords_not_match) { it == state.value.passwordFieldData.value },
                )
            )
        )
    }
}