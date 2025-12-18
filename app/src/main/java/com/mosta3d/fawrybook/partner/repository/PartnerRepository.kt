package com.mosta3d.fawrybook.partner.repository

import com.mosta3d.fawrybook.partner.model.AddPartnerRequest
import com.mosta3d.fawrybook.partner.model.AddPartnerResponse
import com.mosta3d.fawrybook.shared.client.AppResponse

interface PartnerRepository {
    suspend fun addPartner(partner: AddPartnerRequest): AppResponse<AddPartnerResponse?>
}
