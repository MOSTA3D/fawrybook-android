package com.mosta3d.fawrybook.shared.model

abstract class BaseField<T>(
    open val value: T,
    open var touched: Boolean
) {
    abstract fun isValid(): Boolean
}