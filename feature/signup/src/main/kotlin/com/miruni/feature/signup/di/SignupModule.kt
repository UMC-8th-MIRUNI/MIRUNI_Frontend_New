package com.miruni.feature.signup.di

import com.miruni.feature.signup.data.api.SignupApi
import com.miruni.feature.signup.data.datasource.SignupRemoteDataSourceImpl
import com.miruni.feature.signup.data.repository.SignupRepositoryImpl
import com.miruni.feature.signup.domain.datasource.SignupRemoteDataSource
import com.miruni.feature.signup.domain.repository.SignupRepository
import com.miruni.feature.signup.domain.usecase.SendEmailVerifyUseCase
import com.miruni.feature.signup.domain.usecase.SignupUseCase
import com.miruni.feature.signup.domain.usecase.VerifyEmailUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignupModule {
    @Provides
    @Singleton
    fun provideSignupRemoteDataSource(
        signupApi: SignupApi
    ): SignupRemoteDataSource = SignupRemoteDataSourceImpl(signupApi)

    @Provides
    @Singleton
    fun provideSignupRepository(
        signupRemoteDataSource: SignupRemoteDataSource
    ): SignupRepository = SignupRepositoryImpl(signupRemoteDataSource)

    @Provides
    @Reusable
    fun provideSendEmailVerifyUseCase(
        signupRepository: SignupRepository
    ): SendEmailVerifyUseCase = SendEmailVerifyUseCase(signupRepository)

    @Provides
    @Reusable
    fun provideVerifyEmailUseCase(
        signupRepository: SignupRepository
    ): VerifyEmailUseCase = VerifyEmailUseCase(signupRepository)

    @Provides
    @Reusable
    fun provideSignupUseCase(
        signupRepository: SignupRepository
    ): SignupUseCase = SignupUseCase(signupRepository)
}