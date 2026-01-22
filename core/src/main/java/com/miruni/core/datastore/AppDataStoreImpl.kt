package com.miruni.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.miruni.core.domain.common.AppDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AppDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : AppDataStore {

    override suspend fun <T> get(key: Preferences.Key<T>): T? {
        return dataStore.data.firstOrNull()?.get(key)
    }

    override suspend fun <T> put(
        key: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun <T> remove(key: Preferences.Key<T>) {
        dataStore.edit {
            it.remove(key)
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    override fun <T> observe(
        key: Preferences.Key<T>,
        default: T
    ): Flow<T> {
        return dataStore.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e
            }
            .map { prefs -> prefs[key] ?: default }
            .distinctUntilChanged()
    }

    override fun <T> observeNullable(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e
            }
            .map { prefs -> prefs[key] }
            .distinctUntilChanged()
    }
}