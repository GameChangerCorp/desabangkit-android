package id.buildindo.desabangkit.android.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.buildindo.desabangkit.android.core.data.remote.api.ApiConfig
import id.buildindo.desabangkit.android.core.data.remote.response.AddPhotoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PhotoViewModel : ViewModel() {
    private val _addStory = MutableLiveData<AddPhotoResponse>()
    val addStory : LiveData<AddPhotoResponse> = _addStory

    fun addPhoto(photo: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = ApiConfig.apiInstance.checkUploadPhoto(photo)
                if (client.isSuccessful){
                    _addStory.postValue(client.body())
                }else{
                    _addStory.postValue(AddPhotoResponse(error = true))
                }
            }catch (ex : Exception){
                Log.d("Error", ex.toString())
            }
        }
    }
}