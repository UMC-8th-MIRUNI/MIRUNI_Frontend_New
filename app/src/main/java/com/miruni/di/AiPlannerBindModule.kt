package com.miruni.di

import com.miruni.feature.aiplanner.data.repository.MainRepositoryImpl
import com.miruni.feature.aiplanner.data.repository.PlanningRepositoryImpl
import com.miruni.feature.aiplanner.domain.repository.MainRepository
import com.miruni.feature.aiplanner.domain.repository.PlanningRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiPlannerBindModule {

    @Binds
    @Singleton
    abstract fun bindAiPlannerRepository(
        impl: MainRepositoryImpl
    ): MainRepository

    @Binds
    @Singleton
    abstract fun bindPlanningRepository(
        impl: PlanningRepositoryImpl
    ): PlanningRepository
}