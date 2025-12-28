package com.miruni.di

import com.miruni.datastore.OnboardingRepositoryImpl
import com.miruni.feature.aiplanner.data.repository.AiPlannerRepositoryImpl
import com.miruni.feature.aiplanner.domain.AiPlannerRepository
import com.miruni.feature.aiplanner.domain.OnboardingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AiPlannerBindModule {

    @Binds
    abstract fun bindAiPlannerRepository(
        impl: AiPlannerRepositoryImpl
    ): AiPlannerRepository

    @Binds
    abstract fun bindOnboardingRepository(
        impl: OnboardingRepositoryImpl
    ): OnboardingRepository
}