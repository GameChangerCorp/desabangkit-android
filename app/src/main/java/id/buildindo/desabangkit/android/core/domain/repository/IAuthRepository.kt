package id.buildindo.desabangkit.android.core.domain.repository

import id.buildindo.desabangkit.android.core.data.remote.response.CodeVerificationResponse
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import id.buildindo.desabangkit.android.core.data.remote.response.roles.RolesResponse
import retrofit2.Response

interface IAuthRepository {
    suspend fun registerUser(body: RegisterRequest): Response<RegisterResponse>
    suspend fun loginUser(body: LoginRequest): Response<LoginResponse>
    suspend fun rolesUser(): Response<RolesResponse>
    suspend fun sendUserVerification(code: String): Response<CodeVerificationResponse>
    suspend fun resendUserVerification(email: String): Response<CodeVerificationResponse>
}