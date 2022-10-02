package id.buildindo.desabangkit.android.core.data.remote.api

import id.buildindo.desabangkit.android.core.data.remote.response.CodeVerificationResponse
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.ProductHistoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.UploadPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import id.buildindo.desabangkit.android.core.data.remote.response.roles.RolesResponse
import id.buildindo.desabangkit.android.core.data.remote.response.transaction.TransactionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/users/registrations")
    suspend fun registerUser(
        @Body body: RegisterRequest
    ) : Response<RegisterResponse>

    @POST("/users/login")
    suspend fun loginUser(
        @Body body: LoginRequest
    ) : Response<LoginResponse>

    @GET("/users/role")
    suspend fun getUserRole() : Response<RolesResponse>

    @GET("/users/products/transaction")
    suspend fun getTransactionByUserId(
        @Query("id") id: String
    ) : Response<TransactionResponse>

    @GET("/users/products")
    suspend fun getProductHistoryByUserId(
        @Query("id") id: String
    ) : Response<ProductHistoryResponse>

    @GET("/users/products/status")
    suspend fun getProductHistoryByUserIdAndPreorder(
        @Query("id") id: String,
        @Query("preorder") preorder: Boolean
    ) : Response<ProductHistoryResponse>

    @POST("/users/verification-account")
    suspend fun sendUserVerification(
        @Query("code") code : String? = null
    ) : Response<CodeVerificationResponse>

    @PUT("/users/send-verification")
    suspend fun resendVerification(
        @Query("email") email : String? = null
    ) : Response<CodeVerificationResponse>

    @POST("/users/products")
    suspend fun postProducts(
        @Query("preorder") preorder: Boolean? = null,
        @Body body: PostProductRequest
    ): Response<PostProductResponse>

    @Multipart
    @POST("/upload")
    suspend fun uploadPhoto(
        @Part file : MultipartBody.Part,
    ): Response<UploadPhotoResponse>
}
