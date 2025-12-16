package com.mosta3d.fawrybook.auth.state

import com.mosta3d.fawrybook.auth.model.EmailField
import com.mosta3d.fawrybook.auth.model.IsPasswordVisibleField
import com.mosta3d.fawrybook.auth.model.PasswordField
import com.mosta3d.fawrybook.auth.model.SignUpRequest
import com.mosta3d.fawrybook.auth.model.UsernameField
import com.mosta3d.fawrybook.shared.form.AppFieldData
import com.mosta3d.fawrybook.shared.model.BaseField

data class SignUpState(
    val usernameFieldData: AppFieldData<String>,
    val emailFieldData: AppFieldData<String>,
    val passwordFieldData: AppFieldData<String>,
    val confirmPasswordFieldData: AppFieldData<String>,
) {
    fun toRequestPayload(): SignUpRequest {
        return SignUpRequest(
            usernameFieldData.value,
            emailFieldData.value,
            confirmPasswordFieldData.value
        )
    }

    val isValid: Boolean
        get() =
            this::class.java.declaredFields.all {
                val fieldVal = it.get(this)
                fieldVal is AppFieldData<*> && fieldVal.isValid
            }
}
