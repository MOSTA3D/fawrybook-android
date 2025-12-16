package com.mosta3d.fawrybook.auth.model

import com.mosta3d.fawrybook.shared.model.BaseField

data class IsPasswordVisibleField(
    override val value: Boolean,
    override var touched: Boolean
) : BaseField<Boolean>(value, touched) {
    override fun isValid(): Boolean {
        return true
    }
}