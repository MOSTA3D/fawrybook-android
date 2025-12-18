package com.mosta3d.fawrybook.partner.state

import com.mosta3d.fawrybook.auth.model.UsernameField
import com.mosta3d.fawrybook.partner.model.AddPartnerRequest
import com.mosta3d.fawrybook.partner.model.UsernameOrEmailField
import com.mosta3d.fawrybook.shared.form.AppFieldData
import com.mosta3d.fawrybook.shared.model.BaseState

data class AddPartnerState(
    val usernameOrEmailField: AppFieldData<String>,
) : BaseState() {
    fun toAddPartnerRequest(): AddPartnerRequest {
        return AddPartnerRequest(
            usernameOrEmail = usernameOrEmailField.value
        )
    }
}