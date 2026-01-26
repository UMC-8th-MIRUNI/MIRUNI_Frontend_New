package com.miruni.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.miruni.core.domain.auth.TokenDataStore
import kotlinx.coroutines.flow.first

class TokenDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : TokenDataStore {
    private val ACCESS_TOKEN = stringPreferencesKey("access_token")

    override suspend fun getAccessToken(): String? {
        return dataStore.data.first()[ACCESS_TOKEN]
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[ACCESS_TOKEN] = token
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.remove(ACCESS_TOKEN)
        }
    }
}