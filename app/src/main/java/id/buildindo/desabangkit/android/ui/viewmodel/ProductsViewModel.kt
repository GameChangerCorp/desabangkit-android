package id.buildindo.desabangkit.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.UploadPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.category.CategoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.ProductsResponse
import id.buildindo.desabangkit.android.core.domain.usecase.IProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val useCase: IProductUseCase) : ViewModel() {

    private val _allProduct = MutableLiveData<ProductsResponse>()
    val allProduct: LiveData<ProductsResponse> = _allProduct

    private val _categories = MutableLiveData<CategoryResponse>()
    val categories: LiveData<CategoryResponse> = _categories

    private val _productById = MutableLiveData<ProductsResponse>()
    val productById: LiveData<ProductsResponse> = _productById

    private val _postProduct = MutableLiveData<PostProductResponse>()
    val postProduct: LiveData<PostProductResponse> = _postProduct

    private val _uploadPhoto = MutableLiveData<UploadPhotoResponse>()
    val uploadPhoto: LiveData<UploadPhotoResponse> = _uploadPhoto

    fun getAllProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = useCase.getAllProduct()
                if (response.isSuccessful) {
                    _allProduct.postValue(response.body())
                }
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

    fun getProductCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = useCase.getCategories()
                if (response.isSuccessful) {
                    _categories.postValue(response.body())
                }
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

    fun getProductById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = useCase.getProductByCategoryId(id)
                if (response.isSuccessful) {
                    _productById.postValue(response.body())
                }
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }


    fun postProduct(preorder : Boolean? = null, body: PostProductRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = useCase.postProduct(preorder, body)
                if (response.isSuccessful) {
                    _postProduct.postValue(response.body())
                }
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

    fun uploadPhoto(photo: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = useCase.uploadPhoto(photo)
                if (response.isSuccessful) {
                    _uploadPhoto.postValue(response.body())
                }
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

}