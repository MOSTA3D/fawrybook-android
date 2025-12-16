package com.mosta3d.fawrybook.shared.form.validator

data class AppValidator<T>(
    val messageResourceId: Int,
    private val validation: (T) -> Boolean,
) {
    fun validateAndGetMessageId(value: T): Int? {
        return if(validation(value)) null else messageResourceId
    }
}