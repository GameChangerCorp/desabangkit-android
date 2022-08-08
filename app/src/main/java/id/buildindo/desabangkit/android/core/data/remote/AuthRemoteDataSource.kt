package id.buildindo.desabangkit.android.core.data.remote

import id.buildindo.desabangkit.android.core.data.remote.api.ApiService
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(private val service: ApiService) : IAuthRemoteDataSource {
    override suspend fun registerUser(body: RegisterRequest): Response<RegisterResponse> =
      withContext(Dispatchers.IO) {
          service.registerUser(body)
      }

    override suspend fun loginUser(body: LoginRequest): Response<LoginResponse> =
        withContext(Dispatchers.IO) {
            service.loginUser(body)
        }
}