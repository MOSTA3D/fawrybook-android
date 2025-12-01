package com.mosta3d.fawrybook.auth

import com.mosta3d.fawrybook.auth.repository.AuthRepository
import com.mosta3d.fawrybook.auth.repository.AuthRepositoryImpl
import com.mosta3d.fawrybook.shared.client.FBApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun authRepository(fbApiClient: FBApiClient): AuthRepository {
        return AuthRepositoryImpl(fbApiClient)
    }
}