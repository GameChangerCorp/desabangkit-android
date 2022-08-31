package id.buildindo.desabangkit.android.core.data

import id.buildindo.desabangkit.android.core.data.remote.ProductRemoteDataSource
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.UploadPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.category.CategoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.ProductsResponse
import id.buildindo.desabangkit.android.core.domain.repository.IProductRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val remoteDataSource : ProductRemoteDataSource
) : IProductRepository{
    override suspend fun getAllProduct(): Response<ProductsResponse> = remoteDataSource.getAllProduct()
    override suspend fun getCategories(): Response<CategoryResponse> = remoteDataSource.getCategoryProduct()
    override suspend fun getProductByCategoryId(id: String): Response<ProductsResponse> = remoteDataSource.getProductByCategoryId(id)
    override suspend fun postProduct(preorder: Boolean?, body: PostProductRequest): Response<PostProductResponse> = remoteDataSource.postProduct(preorder, body)
    override suspend fun uploadPhoto(photo: MultipartBody.Part): Response<UploadPhotoResponse> = remoteDataSource.uploadPhoto(photo)

}