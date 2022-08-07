package id.buildindo.desabangkit.android.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.buildindo.desabangkit.android.core.data.remote.api.ApiConfig
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class AuthViewModel : ViewModel() {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse : LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse : LiveData<LoginResponse> = _loginResponse

    fun registerUser(body : RegisterRequest){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val client = ApiConfig.apiInstance.registerUser(body)
                if (client.isSuccessful){
                    _registerResponse.postValue(client.body())
                    Log.d("Success: ", client.body().toString())
                }else{
                    val messageError = JSONObject(client.errorBody()!!.charStream().readText())
                    _registerResponse.postValue(RegisterResponse(messages = messageError.getString("messages")))
                }
            }catch (ex: Exception){
                Log.d("ERROR: ", ex.toString())
            }
        }
    }

    fun loginUser(body: LoginRequest){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val client = ApiConfig.apiInstance.loginUser(body)
                if (client.isSuccessful){
                    _loginResponse.postValue(client.body())
                    Log.d("Success: ", client.body().toString())
                }else{
                    val messageError = JSONObject(client.errorBody()!!.charStream().readText())
                    _loginResponse.postValue(LoginResponse(messages = messageError.getString("messages")))
                }
            }catch (ex: Exception){
                Log.d("ERROR: ", ex.toString())
            }
        }
    }
}