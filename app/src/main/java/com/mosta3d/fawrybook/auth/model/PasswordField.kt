package com.mosta3d.fawrybook.auth.model

import com.mosta3d.fawrybook.shared.model.BaseState

data class PasswordField(val passwordValue: String,
                         override var touched: Boolean
) : BaseState<String>(value = passwordValue, touched) {
    override fun isValid(): Boolean {
        return true
    }
}