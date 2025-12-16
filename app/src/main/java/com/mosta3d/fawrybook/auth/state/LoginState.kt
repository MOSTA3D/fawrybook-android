package com.mosta3d.fawrybook.auth.state

import com.mosta3d.fawrybook.auth.model.EmailField
import com.mosta3d.fawrybook.auth.model.IsPasswordVisibleField
import com.mosta3d.fawrybook.auth.model.LoginRequest
import com.mosta3d.fawrybook.auth.model.PasswordField
import com.mosta3d.fawrybook.shared.model.BaseField
import com.mosta3d.fawrybook.shared.model.BaseState

data class LoginState(
    val emailField: EmailField = EmailField(value = "", touched = false),
    val passwordField: PasswordField = PasswordField(value = "", touched = false),
    val isPasswordVisibleField: IsPasswordVisibleField = IsPasswordVisibleField(
        value = false,
        touched = false
    ),
) : BaseState() {
    fun toLoginRequest(): LoginRequest {
        return LoginRequest(emailField.value, passwordField.value)
    }
}