package id.buildindo.desabangkit.android.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreference private constructor(private val dataStore: DataStore<Preferences>) {
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

    fun getOnBoardingState(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[ONBOARDING_STATE] ?: false
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

    suspend fun saveOnBoardingState(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_STATE] = state
        }
    }

    companion object {
        private val LOGIN_STATE = booleanPreferencesKey("login")
        private val BEARER_KEY = stringPreferencesKey("bearer")
        private val USERNAME = stringPreferencesKey("name")
        private val ONBOARDING_STATE = booleanPreferencesKey("onboarding")

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