package com.mosta3d.fawrybook.shared.network

sealed interface NetworkEvent {
    data object Unauthorized : NetworkEvent
    data class AuthExpired(val code: String) : NetworkEvent
}


