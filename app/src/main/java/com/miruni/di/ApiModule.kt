package com.miruni.di

import com.miruni.feature.aiplanner.data.api.AiPlannerApi
import com.miruni.feature.login.data.api.AuthApi
import com.miruni.feature.mypage.data.api.ProfileApi
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

    // auth API
    @Provides
    @Singleton
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi = retrofit.create(AuthApi::class.java)

    // profile API
    @Provides
    @Singleton
    fun provideProfileApi(
        retrofit: Retrofit
    ): ProfileApi = retrofit.create(ProfileApi::class.java)
}