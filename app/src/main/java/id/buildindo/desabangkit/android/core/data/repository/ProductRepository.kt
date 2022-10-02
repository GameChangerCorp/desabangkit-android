package id.buildindo.desabangkit.android.core.data.repository

import id.buildindo.desabangkit.android.core.data.mapper.DataMapper
import id.buildindo.desabangkit.android.core.data.remote.ProductRemoteDataSource
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
import id.buildindo.desabangkit.android.core.domain.repository.IProductRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : IProductRepository {
    override suspend fun getAllProduct(): List<Products> = DataMapper.productsDTOToProducts(
        remoteDataSource.getAllProduct().body()?.data?.products!!
    )

    override suspend fun getCategories(): List<Category> = DataMapper.categoryDTOToCategory(
        remoteDataSource.getCategoryProduct().body()?.data?.productCategories!!
    )

    override suspend fun getProductByCategoryId(id: String): List<Products> =
        DataMapper.productsDTOToProducts(
            remoteDataSource.getProductByCategoryId(id).body()?.data?.products!!
        )

    override suspend fun postProduct(
        preorder: Boolean?,
        body: PostProductRequest
    ): Response<PostProductResponse> = remoteDataSource.postProduct(preorder, body)

    override suspend fun uploadPhoto(photo: MultipartBody.Part): Response<UploadPhotoResponse> =
        remoteDataSource.uploadPhoto(photo)

    override suspend fun getCooperation(
        cooperationId: String
    ): Response<CooperationResponse> = remoteDataSource.getCooperationData(cooperationId)

    override suspend fun getTransactionByUsersId(userId: String): Response<TransactionResponse> =
        remoteDataSource.getTransactionByUserId(userId)

    override suspend fun getProductHistoryByUserId(userId: String): Response<ProductHistoryResponse> =
        remoteDataSource.getProductHistoryByUserId(userId)

    override suspend fun getProductHistoryByUserIdAndPreorder(
        userId: String,
        preorder: Boolean?
    ): Response<ProductHistoryResponse> = remoteDataSource.getProductHistoryByUserIdAndPreorder(
        userId,
        preorder
    )

}