package com.mosta3d.fawrybook.auth.state

import com.mosta3d.fawrybook.auth.model.EmailField
import com.mosta3d.fawrybook.auth.model.IsPasswordVisibleField
import com.mosta3d.fawrybook.auth.model.LoginRequest
import com.mosta3d.fawrybook.auth.model.PasswordField
import com.mosta3d.fawrybook.shared.model.BaseState

data class LoginState(
    val emailField: EmailField = EmailField(emailValue = "", touched = false),
    val passwordField: PasswordField = PasswordField(passwordValue = "", touched = false),
    val isPasswordVisibleField: IsPasswordVisibleField = IsPasswordVisibleField(
        isPasswordVisible = false,
        touched = false
    ),
) {
    fun toLoginRequest(): LoginRequest {
        return LoginRequest(emailField.value, passwordField.value)
    }

    val valid: Boolean
        get() =
            this::class.java.declaredFields.all {
                val fieldVal = it.get(this)
                fieldVal is BaseState<*> && fieldVal.isValid()
            }
}