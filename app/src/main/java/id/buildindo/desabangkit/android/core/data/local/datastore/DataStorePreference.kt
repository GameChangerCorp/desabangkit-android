package id.buildindo.desabangkit.android.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreference @Inject constructor(private val dataStore: DataStore<Preferences>) {
    fun getLoginState(): Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[LOGIN_STATE] ?: false }

    fun getBearerToken(): Flow<String> =
        dataStore.data.map { preferences -> preferences[BEARER_KEY] ?: "" }

    fun getUserName(): Flow<String> =
        dataStore.data.map { preferences -> preferences[USERNAME] ?: "" }

    fun getOnBoardingState(): Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[ONBOARDING_STATE] ?: false }

    fun getUserRoles(): Flow<String> =
        dataStore.data.map { preferences -> preferences[USER_ROLES] ?: "" }

    fun getUserEmail(): Flow<String> =
        dataStore.data.map { preferences -> preferences[USER_EMAIL] ?: "" }

    fun getUserPassword(): Flow<String> =
        dataStore.data.map { preferences -> preferences[USER_PASSWORD] ?: "" }

    fun getUserId(): Flow<String> = dataStore.data.map { preferences -> preferences[USER_ID] ?: "" }

    suspend fun saveLoginState(loginState: Boolean) = dataStore.edit { preferences ->
        preferences[LOGIN_STATE] = loginState
    }

    suspend fun saveBearerToken(bearerKey: String) = dataStore.edit { preferences ->
        preferences[BEARER_KEY] = bearerKey
    }

    suspend fun saveUserName(name: String) = dataStore.edit { preferences ->
        preferences[USERNAME] = name
    }

    suspend fun saveOnBoardingState(state: Boolean) = dataStore.edit { preferences ->
        preferences[ONBOARDING_STATE] = state
    }


    suspend fun saveUserRoles(roles: String) = dataStore.edit { preferences ->
        preferences[USER_ROLES] = roles
    }

    suspend fun saveUserEmail(email: String) = dataStore.edit { preferences ->
        preferences[USER_EMAIL] = email
    }

    suspend fun saveUserPassword(password: String) = dataStore.edit { preferences ->
        preferences[USER_PASSWORD] = password
    }


    suspend fun saveUserId(id: String) = dataStore.edit { preferences ->
        preferences[USER_ID] = id
    }


    companion object {
        private val LOGIN_STATE = booleanPreferencesKey("login")
        private val BEARER_KEY = stringPreferencesKey("bearer")
        private val USERNAME = stringPreferencesKey("name")
        private val ONBOARDING_STATE = booleanPreferencesKey("onboarding")
        private val USER_ROLES = stringPreferencesKey("roles")
        private val USER_EMAIL = stringPreferencesKey("email")
        private val USER_PASSWORD = stringPreferencesKey("password")
        private val USER_ID = stringPreferencesKey("userId")
    }
}