package com.miruni.feature.login.di

import com.miruni.core.domain.auth.TokenDataStore
import com.miruni.feature.login.data.api.AuthApi
import com.miruni.feature.login.data.datasource.AuthLocalDataSourceImpl
import com.miruni.feature.login.data.datasource.AuthRemoteDataSourceImpl
import com.miruni.feature.login.data.repository.AuthRepositoryImpl
import com.miruni.feature.login.domain.datasource.AuthLocalDataSource
import com.miruni.feature.login.domain.datasource.AuthRemoteDataSource
import com.miruni.feature.login.domain.repository.AuthRepository
import com.miruni.feature.login.domain.usecase.GetGoogleLoginUseCase
import com.miruni.feature.login.domain.usecase.GetKakaoLoginUseCase
import com.miruni.feature.login.domain.usecase.GetLoginUseCase
import com.miruni.feature.login.domain.usecase.GetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        authApi: AuthApi
    ): AuthRemoteDataSource = AuthRemoteDataSourceImpl(authApi)

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(
        tokenDataStore: TokenDataStore
    ): AuthLocalDataSource = AuthLocalDataSourceImpl(tokenDataStore)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        authLocalDataSource: AuthLocalDataSource
    ): AuthRepository = AuthRepositoryImpl(authRemoteDataSource, authLocalDataSource)

    @Provides
    @Reusable
    fun provideGetLoginUseCase(
        authRepository: AuthRepository
    ): GetLoginUseCase = GetLoginUseCase(authRepository)

    @Provides
    @Reusable
    fun provideGetKakaoLoginUseCase(
        authRepository: AuthRepository
    ): GetKakaoLoginUseCase = GetKakaoLoginUseCase(authRepository)

    @Provides
    @Reusable
    fun provideGetGoogleLoginUseCase(
        authRepository: AuthRepository
    ): GetGoogleLoginUseCase = GetGoogleLoginUseCase(authRepository)

    @Provides
    @Reusable
    fun provideGetTokenUseCase(
        authRepository: AuthRepository
    ): GetTokenUseCase = GetTokenUseCase(authRepository)

}