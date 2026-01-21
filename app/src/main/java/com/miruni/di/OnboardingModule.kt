package com.miruni.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.miruni.core.datastore.OnboardingRepositoryImpl
import com.miruni.core.domain.onboarding.OnboardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Provides
    @Singleton
    fun provideOnboardingRepository(
        dataStore: DataStore<Preferences>
    ) : OnboardingRepository {
        return OnboardingRepositoryImpl(dataStore)
    }
}