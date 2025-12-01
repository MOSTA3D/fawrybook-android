package com.mosta3d.fawrybook.shared.model

abstract class BaseState<T>(
    open val value: T,
    open var touched: Boolean
) {
    abstract fun isValid(): Boolean
}