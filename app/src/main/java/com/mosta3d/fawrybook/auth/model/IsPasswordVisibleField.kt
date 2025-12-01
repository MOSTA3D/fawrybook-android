package com.mosta3d.fawrybook.auth.model

import com.mosta3d.fawrybook.shared.model.BaseState

data class IsPasswordVisibleField(
    private val isPasswordVisible: Boolean,
    override var touched: Boolean
) : BaseState<Boolean>(isPasswordVisible, touched) {
    override fun isValid(): Boolean {
        return true
    }
}