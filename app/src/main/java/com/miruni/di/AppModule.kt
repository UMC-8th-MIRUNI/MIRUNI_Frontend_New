package com.miruni.di

import android.content.Context
import com.miruni.core.data.fcm.DeviceIdProviderImpl
import com.miruni.core.domain.fcm.DeviceIdProvider
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// app 모듈의 DiModule.kt
@Singleton
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDeviceIdProvider(
        @ApplicationContext context: Context
    ): DeviceIdProvider = DeviceIdProviderImpl(context)
}