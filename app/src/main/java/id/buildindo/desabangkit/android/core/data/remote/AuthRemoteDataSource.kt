package id.buildindo.desabangkit.android.core.data.remote

import id.buildindo.desabangkit.android.core.data.remote.api.ApiService
import id.buildindo.desabangkit.android.core.data.remote.response.CodeVerificationRequest
import id.buildindo.desabangkit.android.core.data.remote.response.CodeVerificationResponse
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import id.buildindo.desabangkit.android.core.data.remote.response.roles.RolesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(private val service: ApiService) :
    IAuthRemoteDataSource {
    override suspend fun registerUser(body: RegisterRequest): Response<RegisterResponse> =
        withContext(Dispatchers.IO) {
            service.registerUser(body)
        }

    override suspend fun loginUser(body: LoginRequest): Response<LoginResponse> =
        withContext(Dispatchers.IO) {
            service.loginUser(body)
        }

    override suspend fun rolesUser(): Response<RolesResponse> =
        withContext(Dispatchers.IO) {
            service.getUserRole()
        }

    override suspend fun sendUserVerification(code: String): Response<CodeVerificationResponse> =
        withContext(Dispatchers.IO) {
            service.sendUserVerification(code)
        }

    override suspend fun resendUserVerification(email: String): Response<CodeVerificationResponse> =
        withContext(Dispatchers.IO) {
            service.resendVerification(email)
        }
}