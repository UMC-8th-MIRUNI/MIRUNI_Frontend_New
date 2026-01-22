package com.miruni.feature.mypage.di

import com.miruni.feature.mypage.data.api.AccountApi
import com.miruni.feature.mypage.data.api.ProfileApi
import com.miruni.feature.mypage.data.datasource.AccountRemoteDataSourceImpl
import com.miruni.feature.mypage.data.datasource.ProfileRemoteDataSourceImpl
import com.miruni.feature.mypage.data.repository.AccountRepositoryImpl
import com.miruni.feature.mypage.data.repository.ProfileRepositoryImpl
import com.miruni.feature.mypage.domain.datasource.AccountRemoteDataSource
import com.miruni.feature.mypage.domain.datasource.ProfileRemoteDataSource
import com.miruni.feature.mypage.domain.repository.AccountRepository
import com.miruni.feature.mypage.domain.repository.ProfileRepository
import com.miruni.feature.mypage.domain.usecase.GetMyPageInfoUseCase
import com.miruni.feature.mypage.domain.usecase.UpdateAccountUseCase
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

    // Profile
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
    fun provideGetMyPageInfoUseCase(
        profileRepository: ProfileRepository
    ): GetMyPageInfoUseCase = GetMyPageInfoUseCase(profileRepository)

    @Provides
    @Reusable
    fun provideUpdateProfileUseCase(
        profileRepository: ProfileRepository
    ): UpdateProfileUseCase = UpdateProfileUseCase(profileRepository)

    // Account
    @Provides
    @Singleton
    fun provideAccountRemoteDataSource(
        accountApi: AccountApi
    ): AccountRemoteDataSource = AccountRemoteDataSourceImpl(accountApi)

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountRemoteDataSource: AccountRemoteDataSource
    ): AccountRepository = AccountRepositoryImpl(accountRemoteDataSource)

    @Provides
    @Reusable
    fun provideUpdateAccountUseCase(
        accountRepository: AccountRepository
    ): UpdateAccountUseCase = UpdateAccountUseCase(accountRepository)
}
