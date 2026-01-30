package com.miruni.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.miruni.core.domain.auth.TokenDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : TokenDataStore {
    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")

    override suspend fun getAccessToken(): String? {
        return dataStore.data.first()[ACCESS_TOKEN]
    }

    override suspend fun getAccessTokenFlow(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[ACCESS_TOKEN] = token
        }
    }

    override suspend fun getRefreshToken(): String? {
        return dataStore.data.first()[REFRESH_TOKEN]
    }

    override suspend fun getRefreshTokenFlow(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        dataStore.edit {
            it[REFRESH_TOKEN] = token
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.remove(ACCESS_TOKEN)
        }
    }
}