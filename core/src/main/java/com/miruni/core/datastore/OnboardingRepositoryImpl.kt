package com.miruni.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.miruni.core.domain.onboarding.OnboardingRepository
import com.miruni.core.domain.onboarding.OnboardingKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnboardingRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : OnboardingRepository {

    /** 각 feature별 온보딩 완료 여부 확인 */
    private fun whatFeature(key: OnboardingKey) =
        booleanPreferencesKey("onboarding_${key.name.lowercase()}")

    override fun isCompleted(key: OnboardingKey): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[whatFeature(key)] ?: false
        }
    }

    override suspend fun completeOnboarding(key: OnboardingKey) {
        dataStore.edit { prefs ->
            prefs[whatFeature(key)] = true
        }
    }
}