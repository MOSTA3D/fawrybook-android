package com.mosta3d.fawrybook.shared.di

import com.mosta3d.fawrybook.BuildConfig
import com.mosta3d.fawrybook.auth.repository.SecretsStore
import com.mosta3d.fawrybook.enums.Token
import com.mosta3d.fawrybook.shared.client.ApiDispatcher
import com.mosta3d.fawrybook.shared.client.FBApiClient
import com.mosta3d.fawrybook.shared.client.KtorApiDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
class SharedModule {
    @Provides
    @Singleton
    fun httpClient(secretsStore: SecretsStore): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true   // Optional: Makes the JSON output pretty-printed
                    isLenient = true     // Optional: Allows lenient parsing of non-strict JSON formats
                    ignoreUnknownKeys = true  // Optional: Ignores unknown keys in JSON
                })
            }

            install(DefaultRequest) {
                headers {
                    append("Authorization", "Bearer ${secretsStore.getItem(Token.ACCESS_TOKEN.name)}")
                }
            }
        }
    }

    @Provides
    @Singleton
    fun fbApiDispatcher(httpClient: HttpClient): ApiDispatcher {
        return KtorApiDispatcher(
            baseUrl = BuildConfig.BASE_URL,
            client = httpClient
        )
    }

    @Provides
    @Singleton
    fun fbApiClient(apiDispatcher: ApiDispatcher): FBApiClient {
        return FBApiClient(apiDispatcher)
    }
}