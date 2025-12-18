package com.mosta3d.fawrybook.auth

import android.content.Context
import com.mosta3d.fawrybook.auth.repository.AuthRepository
import com.mosta3d.fawrybook.auth.repository.AuthRepositoryImpl
import com.mosta3d.fawrybook.auth.repository.AuthTokenRefresher
import com.mosta3d.fawrybook.auth.repository.SecretsStore
import com.mosta3d.fawrybook.auth.repository.SecretsTokenStore
import com.mosta3d.fawrybook.shared.client.FBApiClient
import com.mosta3d.fawrybook.shared.network.TokenRefresher
import com.mosta3d.fawrybook.shared.network.TokenStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun authRepository(fbApiClient: FBApiClient): AuthRepository {
        return AuthRepositoryImpl(fbApiClient)
    }

    @Provides
    @Singleton
    fun secretsStore(@ApplicationContext context: Context): SecretsStore {
        return SecretsStore(context)
    }

    @Provides
    @Singleton
    fun tokenStore(secretsStore: SecretsStore): TokenStore {
        return SecretsTokenStore(secretsStore)
    }
}