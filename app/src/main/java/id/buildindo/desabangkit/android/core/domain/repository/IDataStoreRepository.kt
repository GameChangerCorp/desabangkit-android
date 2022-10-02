package id.buildindo.desabangkit.android.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    fun getLoginState(): Flow<Boolean>
    fun getBearerToken(): Flow<String>
    fun getUserName(): Flow<String>
    fun getOnBoardingState(): Flow<Boolean>
    fun getUserRoles(): Flow<String>
    fun getUserEmail(): Flow<String>
    fun getUserPassword(): Flow<String>
    fun getUserId(): Flow<String>
    suspend fun saveLoginState(loginState: Boolean)
    suspend fun saveBearerToken(bearerKey: String)
    suspend fun saveUserName(name: String)
    suspend fun saveOnBoardingState(state: Boolean)
    suspend fun saveUserRoles(roles: String)
    suspend fun saveUserEmail(email: String)
    suspend fun saveUserPassword(password: String)
    suspend fun saveUserId(id: String)
}