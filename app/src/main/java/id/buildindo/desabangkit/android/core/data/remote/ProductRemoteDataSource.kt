package id.buildindo.desabangkit.android.core.data.remote

import id.buildindo.desabangkit.android.core.data.remote.api.ApiDummyService
import id.buildindo.desabangkit.android.core.data.remote.api.ApiService
import id.buildindo.desabangkit.android.core.data.remote.response.cooperation.CooperationResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.ProductHistoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.UploadPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.category.CategoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.ProductsResponse
import id.buildindo.desabangkit.android.core.data.remote.response.transaction.TransactionResponse
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

    override suspend fun uploadPhoto(photo: MultipartBody.Part): Response<UploadPhotoResponse> =
        service.uploadPhoto(photo)

    override suspend fun getCooperationData(
        cooperationId: String
    ): Response<CooperationResponse> = dummyService.getCooperation(cooperationId)

    override suspend fun getTransactionByUserId(id: String): Response<TransactionResponse> =
        service.getTransactionByUserId(id)

    override suspend fun getProductHistoryByUserId(id: String): Response<ProductHistoryResponse> =
        service.getProductHistoryByUserId(id)

    override suspend fun getProductHistoryByUserIdAndPreorder(
        id: String,
        preorder: Boolean?
    ): Response<ProductHistoryResponse> = service.getProductHistoryByUserIdAndPreorder(
        id,
        preorder!!
    )
}
