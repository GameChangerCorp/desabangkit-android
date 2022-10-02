package id.buildindo.desabangkit.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.buildindo.desabangkit.android.core.data.remote.response.cooperation.CooperationResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.ProductHistoryResponse
import id.buildindo.desabangkit.android.core.data.remote.response.products.UploadPhotoResponse
import id.buildindo.desabangkit.android.core.data.remote.response.transaction.TransactionResponse
import id.buildindo.desabangkit.android.core.domain.model.Category
import id.buildindo.desabangkit.android.core.domain.model.Products
import id.buildindo.desabangkit.android.core.domain.usecase.IProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val useCase: IProductUseCase) : ViewModel() {

    private val _allProduct = MutableLiveData<List<Products>>()
    val allProduct: LiveData<List<Products>> = _allProduct

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _productById = MutableLiveData<List<Products>>()
    val productById: LiveData<List<Products>> = _productById

    private val _postProduct = MutableLiveData<PostProductResponse>()
    val postProduct: LiveData<PostProductResponse> = _postProduct

    private val _uploadPhoto = MutableLiveData<UploadPhotoResponse>()
    val uploadPhoto: LiveData<UploadPhotoResponse> = _uploadPhoto

    private val _cooperation = MutableLiveData<CooperationResponse>()
    val cooperation: LiveData<CooperationResponse> = _cooperation

    private val _transaction = MutableLiveData<TransactionResponse>()
    val transaction: LiveData<TransactionResponse> = _transaction

    private val _productHistory = MutableLiveData<ProductHistoryResponse>()
    val productHistory: LiveData<ProductHistoryResponse> = _productHistory

    private val _productHistoryWithPreorder = MutableLiveData<ProductHistoryResponse>()
    val productHistoryWithPreorder: LiveData<ProductHistoryResponse> = _productHistoryWithPreorder

    fun getAllProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allProduct.postValue(useCase.getAllProduct())
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

    fun getProductCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _categories.postValue(useCase.getCategories())
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

    fun getProductById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _productById.postValue(useCase.getProductByCategoryId(id))
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }


    fun postProduct(preorder: Boolean? = null, body: PostProductRequest) {
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

    fun cooperationData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = useCase.getCooperationData(id)
                if (response.isSuccessful) {
                    _cooperation.postValue(response.body())
                }
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

    fun getTransactionData(id: String) {
        viewModelScope.launch {
            try {
                val response = useCase.getTransactionByUserId(id)
                if (response.isSuccessful) {
                    _transaction.postValue(response.body())
                }
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

    fun getProductHistory(id: String) {
        viewModelScope.launch {
            try {
                val response = useCase.getProductHistoryByUserId(id)
                if (response.isSuccessful) {
                    _productHistory.postValue(response.body())
                }
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

    fun getProductHistoryWithPreorderStatus(id: String, preorder: Boolean? = null) {
        viewModelScope.launch {
            try {
                val response = useCase.getProductHistoryByUserIdAndPreorder(id, preorder)
                if (response.isSuccessful) {
                    _productHistoryWithPreorder.postValue(response.body())
                }
            } catch (ex: Exception) {
                Timber.d("gagal dapat data karena ${ex.message}")
            }
        }
    }

}