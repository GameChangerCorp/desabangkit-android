package id.buildindo.desabangkit.android.core.data.mapper

import id.buildindo.desabangkit.android.core.data.remote.response.products.category.CategoryDTO
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.ProductsDTO
import id.buildindo.desabangkit.android.core.domain.model.Category
import id.buildindo.desabangkit.android.core.domain.model.Products

object DataMapper {
    fun productsDTOToProducts(productsDTO: List<ProductsDTO>): List<Products> {
        val products = ArrayList<Products>()
        productsDTO.map {
            val product = Products(
                id = it.id,
                productsId = it.sku,
                category = it.category,
                name = it.name,
                unit = it.unit,
                price = it.price,
                photo = it.photoUrl
            )
            products.add(product)
        }
        return products
    }

    fun categoryDTOToCategory(categoryDTO: List<CategoryDTO>): List<Category> {
        val categories = ArrayList<Category>()
        categoryDTO.map {
            val category = Category(
                id = it.id,
                name = it.name
            )
            categories.add(category)
        }
        return categories
    }

}