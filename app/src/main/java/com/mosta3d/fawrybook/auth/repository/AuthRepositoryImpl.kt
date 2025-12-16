package com.mosta3d.fawrybook.auth.repository

import com.mosta3d.fawrybook.auth.model.LoginRequest
import com.mosta3d.fawrybook.auth.model.LoginResponse
import com.mosta3d.fawrybook.auth.model.SignUpRequest
import com.mosta3d.fawrybook.auth.model.SignUpResponse
import com.mosta3d.fawrybook.shared.client.AppResponse
import com.mosta3d.fawrybook.shared.client.FBApiClient
import com.mosta3d.fawrybook.shared.client.Options
import io.ktor.util.reflect.typeInfo
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val fbApiClient: FBApiClient) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): AppResponse<LoginResponse?> {
        val response = fbApiClient.post<LoginRequest, LoginResponse?>(
            body = loginRequest,
            segments = arrayOf("auth", "login"),
            options = Options(
                responseType = typeInfo<AppResponse<LoginResponse>>()
            )
        )
        return response
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): AppResponse<SignUpResponse?> {
        val response = fbApiClient.post<SignUpRequest, SignUpResponse?>(
            body = signUpRequest,
            segments = arrayOf("auth", "signup"),
            options = Options(
                responseType = typeInfo<AppResponse<SignUpResponse>>()
            )
        )

        return response
    }
}