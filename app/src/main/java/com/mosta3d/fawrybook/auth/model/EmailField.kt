package com.mosta3d.fawrybook.auth.model

import android.util.Patterns
import com.mosta3d.fawrybook.shared.model.BaseState

data class EmailField(
    private val emailValue: String,
    override var touched: Boolean
) : BaseState<String>(value = emailValue, touched) {
    override fun isValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value)
            .matches()
    }
}