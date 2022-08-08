package id.buildindo.desabangkit.android.core.data

import id.buildindo.desabangkit.android.core.data.remote.AuthRemoteDataSource
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import id.buildindo.desabangkit.android.core.domain.repository.IAuthRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val remote : AuthRemoteDataSource
) : IAuthRepository{
    override suspend fun registerUser(body: RegisterRequest): Response<RegisterResponse> = remote.registerUser(body)
    override suspend fun loginUser(body: LoginRequest): Response<LoginResponse> = remote.loginUser(body)
}