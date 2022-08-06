package id.buildindo.desabangkit.android

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/upload")
    suspend fun checkUploadPhoto(
        @Part file : MultipartBody.Part
    ): Response<AddPhotoResponse>
}