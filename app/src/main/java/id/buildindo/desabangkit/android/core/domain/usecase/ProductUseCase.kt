package id.buildindo.desabangkit.android.core.domain.usecase

import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.UploadPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.category.CategoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.ProductsResponse
import id.buildindo.desabangkit.android.core.domain.repository.IProductRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val repository: IProductRepository) :
    IProductUseCase {
    override suspend fun getAllProduct(): Response<ProductsResponse> = repository.getAllProduct()
    override suspend fun getCategories(): Response<CategoryResponse> = repository.getCategories()
    override suspend fun getProductByCategoryId(id: String): Response<ProductsResponse> =
        repository.getProductByCategoryId(id)

    override suspend fun postProduct(preorder: Boolean?, body: PostProductRequest): Response<PostProductResponse> =
        repository.postProduct(preorder, body)

    override suspend fun uploadPhoto(photo: MultipartBody.Part): Response<UploadPhotoResponse> =
        repository.uploadPhoto(photo)
}