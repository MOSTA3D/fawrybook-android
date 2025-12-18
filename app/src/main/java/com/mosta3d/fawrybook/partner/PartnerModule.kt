package com.mosta3d.fawrybook.partner

import com.mosta3d.fawrybook.partner.repository.PartnerRepository
import com.mosta3d.fawrybook.partner.repository.PartnerRepositoryImpl
import com.mosta3d.fawrybook.shared.client.FBApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PartnerModule {
    @Provides
    @Singleton
    fun partnerRepository(fbApiClient: FBApiClient): PartnerRepository {
        return PartnerRepositoryImpl(fbApiClient)
    }
}