package com.miruni.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.miruni.core.datastore.AppDataStoreImpl
import com.miruni.core.domain.common.AppDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDataStoreModule {
    @Provides
    @Singleton
    fun provideAppDataStore(
        dataStore: DataStore<Preferences>
    ): AppDataStore = AppDataStoreImpl(dataStore)
}