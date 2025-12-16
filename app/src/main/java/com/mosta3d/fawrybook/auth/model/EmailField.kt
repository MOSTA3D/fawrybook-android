package com.mosta3d.fawrybook.auth.model

import android.util.Patterns
import com.mosta3d.fawrybook.shared.model.BaseField

data class EmailField(
    override val value: String,
    override var touched: Boolean
) : BaseField<String>(value = value, touched) {
    override fun isValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value)
            .matches()
    }
}