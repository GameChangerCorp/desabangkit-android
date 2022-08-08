package id.buildindo.desabangkit.android.core.domain.usecase

import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import id.buildindo.desabangkit.android.core.domain.repository.IAuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val repository: IAuthRepository) : IAuthUseCase {
    override suspend fun registerUser(body: RegisterRequest): Response<RegisterResponse> = repository.registerUser(body)
    override suspend fun loginUser(body: LoginRequest): Response<LoginResponse> = repository.loginUser(body)
}