package com.javimartd.theguardian.v2.data.datasources.disk.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

class PreferencesDataStoreDelegate @Inject constructor(private val appContext: Context) {

    private val TAG = "DataStoreDelegateExtension"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "the_guardian_data_store"
    )

    private object PreferencesKeys {
        val KEY_COUNTER = intPreferencesKey("key_int")
        val KEY_STRING = stringPreferencesKey("key_string")
        val KEY_BOOLEAN = booleanPreferencesKey("key_boolean")
        val KEY_LONG = longPreferencesKey("key_long")
        val KEY_FLOAT = floatPreferencesKey("key_float")
        val KEY_DOUBLE = doublePreferencesKey("key_double")
    }

    // Read from a Preferences DataStore
    val counter = appContext.dataStore.data.map { it[PreferencesKeys.KEY_COUNTER]?: 0 }

    // Write to a Preferences DataStore
    suspend fun incrementCounter() {
        appContext.dataStore.edit {
            val currentCounterValue = it[PreferencesKeys.KEY_COUNTER]?: 0
            it[PreferencesKeys.KEY_COUNTER] = currentCounterValue + 1
        }
    }

    private fun <T>findValue(key: Preferences.Key<T>, defaultValue: T) =
        appContext.dataStore.data
            .catch {exception ->
                if (exception is IOException) {
                    Log.e(TAG, "Error reading preferences.", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }

            }.map {
                it[key]?: defaultValue
            }

    private suspend fun <T>saveValue(key: Preferences.Key<T>, value: T) {
        appContext.dataStore.edit { it[key] = value }
    }
}