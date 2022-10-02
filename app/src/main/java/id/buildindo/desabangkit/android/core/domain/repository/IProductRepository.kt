package id.buildindo.desabangkit.android.core.domain.repository

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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface IProductRepository {
    suspend fun getAllProduct() : List<Products>
    suspend fun getCategories() : List<Category>
    suspend fun getProductByCategoryId(id: String) : List<Products>
    suspend fun postProduct(preorder: Boolean? = null, body: PostProductRequest) : Response<PostProductResponse>
    suspend fun uploadPhoto(photo: MultipartBody.Part) : Response<UploadPhotoResponse>
    suspend fun getCooperation(cooperationId: String) : Response<CooperationResponse>
    suspend fun getTransactionByUsersId(userId: String) : Response<TransactionResponse>
    suspend fun getProductHistoryByUserId(userId: String) : Response<ProductHistoryResponse>
    suspend fun getProductHistoryByUserIdAndPreorder(userId: String, preorder: Boolean?) : Response<ProductHistoryResponse>
}