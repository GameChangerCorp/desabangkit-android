package id.buildindo.desabangkit.android.core.data.remote

import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.UploadPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.category.CategoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.ProductsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface IProductRemoteDataSource {
    suspend fun getAllProduct(): Response<ProductsResponse>
    suspend fun getCategoryProduct(): Response<CategoryResponse>
    suspend fun getProductByCategoryId(id: String): Response<ProductsResponse>
    suspend fun postProduct(
       preorder : Boolean? = null,
       body: PostProductRequest
    ) : Response<PostProductResponse>
    suspend fun uploadPhoto(
        photo: MultipartBody.Part
    ) : Response<UploadPhotoResponse>
}