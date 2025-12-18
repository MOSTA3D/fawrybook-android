package com.mosta3d.fawrybook.shared.form

import com.mosta3d.fawrybook.shared.form.validator.AppValidator

data class AppFieldData<T>(
    val value: T,
    val isTouched: Boolean = false,
    private val validators: List<AppValidator<T>>
) {
    val isValid: Boolean
        get() = validators.all { it.validateAndGetMessageId(value) == null }

    val errorMessages: List<Int>
        get() = validators.mapNotNull {
            it.validateAndGetMessageId(value)
        }

    fun touch(): AppFieldData<T> {
        return this.copy(
            isTouched = true
        )
    }
}