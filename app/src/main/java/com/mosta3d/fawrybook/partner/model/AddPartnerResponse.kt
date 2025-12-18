package com.mosta3d.fawrybook.partner.model

import kotlinx.serialization.Serializable

@Serializable
data class AddPartnerResponse(
    val email: String,
    val username: String,
    val code: String? = null
)
