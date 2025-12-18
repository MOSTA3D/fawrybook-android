package com.mosta3d.fawrybook.partner.model

import kotlinx.serialization.Serializable

@Serializable
data class AddPartnerRequest(
    val usernameOrEmail: String
)
