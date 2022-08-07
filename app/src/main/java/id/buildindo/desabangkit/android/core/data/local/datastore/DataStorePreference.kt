package id.buildindo.desabangkit.android.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val LOGIN_STATE = booleanPreferencesKey("login")
    private val BEARER_KEY = stringPreferencesKey("bearer")
    private val USERNAME = stringPreferencesKey("name")

    fun getLoginState(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[LOGIN_STATE] ?: false
        }
    }

    fun getBearerToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[BEARER_KEY] ?: ""
        }
    }

    fun getUserName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USERNAME] ?: ""
        }
    }


    suspend fun saveLoginState(loginState: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATE] = loginState
        }
    }

    suspend fun saveBearerToken(bearerKey: String) {
        dataStore.edit { prefences ->
            prefences[BEARER_KEY] = bearerKey
        }
    }

    suspend fun saveUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME] = name
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DataStorePreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): DataStorePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStorePreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}