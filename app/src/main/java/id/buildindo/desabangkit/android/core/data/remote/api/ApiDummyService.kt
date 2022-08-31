package id.buildindo.desabangkit.android.core.data.remote.api

import id.buildindo.desabangkit.android.core.data.remote.response.products.category.CategoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDummyService {
    @GET("/products")
    suspend fun getAllProduct() : Response<ProductsResponse>

    @GET("/product-categories")
    suspend fun getCategories() : Response<CategoryResponse>

    @GET("/products")
    suspend fun getProductByCategoryId(
        @Query("categoryId") id : String
    ) : Response<ProductsResponse>
}