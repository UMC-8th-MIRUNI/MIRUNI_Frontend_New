package com.miruni.di

import com.miruni.core.data.fcm.FcmApi
import com.miruni.core.data.fcm.FcmRemoteDataSourceImpl
import com.miruni.core.data.fcm.FcmRepositoryImpl
import com.miruni.core.domain.fcm.FcmRemoteDataSource
import com.miruni.core.domain.fcm.FcmRepository
import com.miruni.core.domain.fcm.RegisterFcmTokenUseCase
import com.miruni.core.domain.fcm.UpdateFcmTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FcmModule {
    @Provides
    @Singleton
    fun provideFcmApi(retrofit: Retrofit) : FcmApi {
        return retrofit.create(FcmApi::class.java)
    }
    @Provides
    @Singleton
    fun provideFcmRemoteDataSource(fcmApi: FcmApi) : FcmRemoteDataSource {
        return FcmRemoteDataSourceImpl(fcmApi)
    }
    @Provides
    @Singleton
    fun provideFcmRepository(fcmRemoteDataSource: FcmRemoteDataSource) : FcmRepository {
        return FcmRepositoryImpl(fcmRemoteDataSource)
    }
    @Provides
    @Singleton
    fun provideRegisterFcmTokenUseCase(fcmRepository: FcmRepository)
    : RegisterFcmTokenUseCase {
        return RegisterFcmTokenUseCase(fcmRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateFcmTokenUseCase(fcmRepository: FcmRepository)
    : UpdateFcmTokenUseCase {
        return UpdateFcmTokenUseCase(fcmRepository)
    }

}