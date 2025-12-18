package com.mosta3d.fawrybook.partner.repository

import com.mosta3d.fawrybook.partner.model.AddPartnerRequest
import com.mosta3d.fawrybook.partner.model.AddPartnerResponse
import com.mosta3d.fawrybook.shared.client.AppResponse
import com.mosta3d.fawrybook.shared.client.FBApiClient
import jakarta.inject.Inject


class PartnerRepositoryImpl @Inject constructor(private val fbApiClient: FBApiClient) : PartnerRepository {
    override suspend fun addPartner(partner: AddPartnerRequest): AppResponse<AddPartnerResponse?> {
        return fbApiClient.post(
            body = partner,
            segments = arrayOf("me", "partner"),
        )
    }
}