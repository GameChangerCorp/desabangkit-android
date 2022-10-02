package id.buildindo.desabangkit.android.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import id.buildindo.desabangkit.android.core.domain.repository.IDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(private val dataStore: DataStore<Preferences>) :
    IDataStoreRepository {
    override fun getLoginState(): Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[LOGIN_STATE] ?: false }

    override fun getBearerToken(): Flow<String> =
        dataStore.data.map { preferences -> preferences[BEARER_KEY] ?: "" }

    override fun getUserName(): Flow<String> =
        dataStore.data.map { preferences -> preferences[USERNAME] ?: "" }

    override fun getOnBoardingState(): Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[ONBOARDING_STATE] ?: false }

    override fun getUserRoles(): Flow<String> =
        dataStore.data.map { preferences -> preferences[USER_ROLES] ?: "" }

    override fun getUserEmail(): Flow<String> =
        dataStore.data.map { preferences -> preferences[USER_EMAIL] ?: "" }

    override fun getUserPassword(): Flow<String> =
        dataStore.data.map { preferences -> preferences[USER_PASSWORD] ?: "" }

    override fun getUserId(): Flow<String> =
        dataStore.data.map { preferences -> preferences[USER_ID] ?: "" }

    override suspend fun saveLoginState(loginState: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATE] = loginState
        }
    }

    override suspend fun saveBearerToken(bearerKey: String) {
        dataStore.edit { preferences ->
            preferences[BEARER_KEY] = bearerKey
        }
    }

    override suspend fun saveUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME] = name
        }
    }

    override suspend fun saveOnBoardingState(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_STATE] = state
        }
    }

    override suspend fun saveUserRoles(roles: String) {
        dataStore.edit { preferences ->
            preferences[USER_ROLES] = roles
        }
    }

    override suspend fun saveUserEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
        }
    }

    override suspend fun saveUserPassword(password: String) {
        dataStore.edit { preferences ->
            preferences[USER_PASSWORD] = password
        }
    }

    override suspend fun saveUserId(id: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = id
        }
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