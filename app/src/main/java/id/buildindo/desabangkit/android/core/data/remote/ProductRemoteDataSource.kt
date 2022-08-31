package id.buildindo.desabangkit.android.core.data.remote

import id.buildindo.desabangkit.android.core.data.remote.api.ApiDummyService
import id.buildindo.desabangkit.android.core.data.remote.api.ApiService
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.UploadPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.category.CategoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.ProductsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSource @Inject constructor(
    private val service: ApiService,
    private val dummyService: ApiDummyService
) : IProductRemoteDataSource {
    override suspend fun getAllProduct(): Response<ProductsResponse> = dummyService.getAllProduct()
    override suspend fun getCategoryProduct(): Response<CategoryResponse> =
        dummyService.getCategories()

    override suspend fun getProductByCategoryId(id: String): Response<ProductsResponse> =
        dummyService.getProductByCategoryId(id)

    override suspend fun postProduct(
        preorder: Boolean?,
        body: PostProductRequest
    ): Response<PostProductResponse> = service.postProducts(preorder, body)

    override suspend fun uploadPhoto(photo: MultipartBody.Part): Response<UploadPhotoResponse> = service.uploadPhoto(photo)
}
