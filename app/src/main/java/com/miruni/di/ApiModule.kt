package com.miruni.di

import com.miruni.feature.aiplanner.data.api.AiPlannerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    /**
     * AI 플래너 API
     */
    @Provides
    @Singleton
    fun provideAiPlannerApi(
        retrofit: Retrofit
    ): AiPlannerApi = retrofit.create(AiPlannerApi::class.java)
}