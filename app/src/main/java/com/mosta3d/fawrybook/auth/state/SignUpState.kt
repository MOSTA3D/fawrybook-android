package com.mosta3d.fawrybook.auth.state

import com.mosta3d.fawrybook.auth.model.SignUpRequest
import com.mosta3d.fawrybook.shared.form.AppFieldData

// todo : use map instead of statically set fields
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
                return fieldVal !is AppFieldData<*> || fieldVal.isValid
            }

    fun asTouched(): SignUpState {
        // todo : find a more dynamic approach
        return this.copy(
            usernameFieldData = usernameFieldData.touch(),
            emailFieldData = emailFieldData.touch(),
            passwordFieldData = passwordFieldData.touch(),
            confirmPasswordFieldData = confirmPasswordFieldData.touch()
        )
    }

    val isTouched: Boolean
        get() = this::class.java.declaredFields.any {
            (it.get(this) as AppFieldData<*>).isTouched
        }
}
