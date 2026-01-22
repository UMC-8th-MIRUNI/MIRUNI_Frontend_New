package com.miruni.feature.pwreset.di

import com.miruni.feature.pwreset.data.api.PwApi
import com.miruni.feature.pwreset.data.datasource.PwRemoteDataSourceImpl
import com.miruni.feature.pwreset.data.repository.PwRepositoryImpl
import com.miruni.feature.pwreset.domain.datasource.PwRemoteDataSource
import com.miruni.feature.pwreset.domain.repository.PwRepository
import com.miruni.feature.pwreset.domain.usecase.SendEmailVerifyUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PwModule {
    @Provides
    @Singleton
    fun providePwRemoteDataSource(
        pwApi: PwApi
    ): PwRemoteDataSource = PwRemoteDataSourceImpl(
        pwApi
    )

    @Provides
    @Singleton
    fun providePwRepository(
        pwRemoteDataSource: PwRemoteDataSource
    ): PwRepository = PwRepositoryImpl(
        pwRemoteDataSource
    )

    @Provides
    @Reusable
    fun provideSendEmailVerifyUseCase(
        pwRepository: PwRepository
    ) : SendEmailVerifyUseCase = SendEmailVerifyUseCase(
        pwRepository
    )
}