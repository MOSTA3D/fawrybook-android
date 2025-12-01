package com.mosta3d.fawrybook.auth.repository

import com.mosta3d.fawrybook.auth.model.LoginRequest
import com.mosta3d.fawrybook.auth.model.LoginResponse
import com.mosta3d.fawrybook.shared.client.AppResponse

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): AppResponse<LoginResponse?>
}
