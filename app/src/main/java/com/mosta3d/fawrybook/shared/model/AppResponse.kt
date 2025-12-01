package com.mosta3d.fawrybook.shared.model

data class AppResponse<T>(val code: String, val data: T, val errors: List<String>)