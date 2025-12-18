package com.mosta3d.fawrybook.auth.state

import com.mosta3d.fawrybook.R
import com.mosta3d.fawrybook.auth.model.EmailField
import com.mosta3d.fawrybook.auth.model.IsPasswordVisibleField
import com.mosta3d.fawrybook.auth.model.LoginRequest
import com.mosta3d.fawrybook.auth.model.PasswordField
import com.mosta3d.fawrybook.shared.form.AppFieldData
import com.mosta3d.fawrybook.shared.form.validator.AppValidator
import com.mosta3d.fawrybook.shared.model.BaseField
import com.mosta3d.fawrybook.shared.model.BaseState

data class LoginState(
    val emailField: AppFieldData<String> = AppFieldData(
        value = "",
        validators = listOf(AppValidator(R.string.username_required) { it.isNotEmpty() })
    ),
    val passwordField: AppFieldData<String> = AppFieldData(
        value = "",
        validators = listOf(AppValidator(R.string.password_required) { it.isNotEmpty() })
    ),
) : BaseState() {
    fun toLoginRequest(): LoginRequest {
        return LoginRequest(emailField.value, passwordField.value)
    }
}