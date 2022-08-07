package id.buildindo.desabangkit.android.core.data.remote.api

import id.buildindo.desabangkit.android.core.data.remote.response.AddPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("/users/registrations")
    suspend fun registerUser(
        @Body body: RegisterRequest
    ) : Response<RegisterResponse>

    @POST("users/login")
    suspend fun loginUser(
        @Body body: LoginRequest
    ) : Response<LoginResponse>

    @Multipart
    @POST("/upload")
    suspend fun checkUploadPhoto(
        @Part file : MultipartBody.Part
    ): Response<AddPhotoResponse>
}