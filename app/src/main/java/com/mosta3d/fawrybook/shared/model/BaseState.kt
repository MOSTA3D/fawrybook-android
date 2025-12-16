package com.mosta3d.fawrybook.shared.model

open class BaseState {
    val valid: Boolean
        get() =
            this::class.java.declaredFields.all {
                val fieldVal = it.get(this)
                fieldVal is BaseField<*> && fieldVal.isValid()
            }
}