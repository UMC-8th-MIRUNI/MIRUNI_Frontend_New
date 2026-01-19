package com.miruni.feature.mypage.di

import com.miruni.feature.mypage.data.api.ProfileApi
import com.miruni.feature.mypage.data.datasource.ProfileRemoteDataSourceImpl
import com.miruni.feature.mypage.data.repository.ProfileRepositoryImpl
import com.miruni.feature.mypage.domain.datasource.ProfileRemoteDataSource
import com.miruni.feature.mypage.domain.repository.ProfileRepository
import com.miruni.feature.mypage.domain.usecase.UpdateProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileRemoteDataSource(
        profileApi: ProfileApi
    ): ProfileRemoteDataSource = ProfileRemoteDataSourceImpl(profileApi)

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileRemoteDataSource: ProfileRemoteDataSource
    ): ProfileRepository = ProfileRepositoryImpl(profileRemoteDataSource)

    @Provides
    @Reusable
    fun provideUpdateProfileUseCase(
        profileRepository: ProfileRepository
    ): UpdateProfileUseCase = UpdateProfileUseCase(profileRepository)
}
