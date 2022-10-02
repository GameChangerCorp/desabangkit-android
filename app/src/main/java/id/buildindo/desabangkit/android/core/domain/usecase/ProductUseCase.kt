package id.buildindo.desabangkit.android.core.domain.usecase

import id.buildindo.desabangkit.android.core.data.remote.response.cooperation.CooperationResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.ProductHistoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.UploadPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.category.CategoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.ProductsResponse
import id.buildindo.desabangkit.android.core.data.remote.response.transaction.TransactionResponse
import id.buildindo.desabangkit.android.core.domain.model.Category
import id.buildindo.desabangkit.android.core.domain.model.Products
import id.buildindo.desabangkit.android.core.domain.repository.IDataStoreRepository
import id.buildindo.desabangkit.android.core.domain.repository.IProductRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: IProductRepository
) :
    IProductUseCase {
    override suspend fun getAllProduct(): List<Products> = repository.getAllProduct()
    override suspend fun getCategories(): List<Category> = repository.getCategories()
    override suspend fun getProductByCategoryId(id: String): List<Products> =
        repository.getProductByCategoryId(id)

    override suspend fun postProduct(
        preorder: Boolean?,
        body: PostProductRequest
    ): Response<PostProductResponse> = repository.postProduct(preorder, body)

    override suspend fun uploadPhoto(photo: MultipartBody.Part): Response<UploadPhotoResponse> =
        repository.uploadPhoto(photo)

    override suspend fun getCooperationData(cooperationId: String): Response<CooperationResponse> =
        repository.getCooperation(cooperationId)

    override suspend fun getTransactionByUserId(userId: String): Response<TransactionResponse> =
        repository.getTransactionByUsersId(userId)

    override suspend fun getProductHistoryByUserId(userId: String): Response<ProductHistoryResponse> =
        repository.getProductHistoryByUserId(userId)

    override suspend fun getProductHistoryByUserIdAndPreorder(
        userId: String,
        preorder: Boolean?
    ): Response<ProductHistoryResponse> =
        repository.getProductHistoryByUserIdAndPreorder(userId, preorder)
}