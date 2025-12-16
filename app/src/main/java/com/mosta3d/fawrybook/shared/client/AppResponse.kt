package com.mosta3d.fawrybook.shared.client

import kotlinx.serialization.Serializable

@Serializable
data class AppResponse<T>(val data: T?, val code: String, val messages: List<String>) {
    val success: Boolean
        get() = code == "0"
}