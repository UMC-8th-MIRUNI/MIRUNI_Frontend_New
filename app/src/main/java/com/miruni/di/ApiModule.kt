package com.miruni.di

import com.miruni.feature.aiplanner.data.api.AiPlannerApi
import com.miruni.feature.home.data.api.HomeApi
import com.miruni.feature.login.data.api.AuthApi
import com.miruni.feature.mypage.data.api.AccountApi
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

    @Provides
    @Singleton
    fun provideHomeApi(
        retrofit: Retrofit
    ): HomeApi = retrofit.create(HomeApi::class.java)

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

    // account API
    @Provides
    @Singleton
    fun provideAccountApi(
        retrofit: Retrofit
    ): AccountApi = retrofit.create(AccountApi::class.java)
}