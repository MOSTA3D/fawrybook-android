package com.mosta3d.fawrybook.partner.event

sealed class AddPartnerEvent {
    data class Success(
        val email: String,
        val username: String,
        val code: String? = null
    ) : AddPartnerEvent()

    data class Error(val messages: List<String>) : AddPartnerEvent()
}