package com.mosta3d.fawrybook.auth.model

import com.mosta3d.fawrybook.shared.model.BaseField

data class PasswordField(
    override val value: String,
    override var touched: Boolean
) : BaseField<String>(value = value, touched) {
    override fun isValid(): Boolean {
        return true
    }
}