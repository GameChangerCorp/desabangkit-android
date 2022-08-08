package id.buildindo.desabangkit.android.core.data.remote

import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import retrofit2.Response

interface IAuthRemoteDataSource {
    suspend fun registerUser(body: RegisterRequest) : Response<RegisterResponse>
    suspend fun loginUser(body: LoginRequest) : Response<LoginResponse>
}