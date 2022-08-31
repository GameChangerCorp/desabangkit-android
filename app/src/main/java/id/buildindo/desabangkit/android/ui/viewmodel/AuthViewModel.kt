package id.buildindo.desabangkit.android.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.buildindo.desabangkit.android.core.data.remote.response.CodeVerificationRequest
import id.buildindo.desabangkit.android.core.data.remote.response.CodeVerificationResponse
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginResponse
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterResponse
import id.buildindo.desabangkit.android.core.data.remote.response.roles.RolesResponse
import id.buildindo.desabangkit.android.core.domain.usecase.IAuthUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val useCase: IAuthUseCase) : ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _rolesResponse = MutableLiveData<RolesResponse>()
    val rolesResponse: LiveData<RolesResponse> = _rolesResponse

    private val _sendVerification = MutableLiveData<CodeVerificationResponse>()
    val sendVerification: LiveData<CodeVerificationResponse> = _sendVerification

    private val _resendVerification = MutableLiveData<CodeVerificationResponse>()
    val resendVerification: LiveData<CodeVerificationResponse> = _resendVerification

    fun registerUser(body: RegisterRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = useCase.registerUser(body)
                if (response.isSuccessful) {
                    _registerResponse.postValue(response.body())
                } else {
                    val messageError = JSONObject(response.errorBody()!!.charStream().readText())
                    _registerResponse.postValue(
                        RegisterResponse(
                            messages = messageError.getString("messages"),
                            code = messageError.getInt("code")
                        )
                    )
                }
            } catch (ex: Exception) {
                Log.d("ERROR: ", ex.toString())
            }
        }
    }

    fun loginUser(body: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = useCase.loginUser(body)
                if (client.isSuccessful) {
                    _loginResponse.postValue(client.body())
                    Log.d("Success: ", client.body().toString())
                } else {
                    val messageError = JSONObject(client.errorBody()!!.charStream().readText())
                    _loginResponse.postValue(
                        LoginResponse(
                            code = messageError.getInt("code"),
                            messages = messageError.getString("messages")
                        )
                    )
                }
            } catch (ex: Exception) {
                Log.d("ERROR: ", ex.toString())
            }
        }
    }

    fun rolesUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = useCase.rolesUser()
                if (client.isSuccessful) {
                    _rolesResponse.postValue(client.body())
                } else {
                    val messageError = JSONObject(client.errorBody()!!.charStream().readText())
                    _rolesResponse.postValue(RolesResponse(messages = messageError.getString("messages")))
                }
            } catch (ex: Exception) {
                Timber.d(ex.toString())
            }
        }
    }

    fun sendVerification(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = useCase.sendUserVerification(code)
                if (client.isSuccessful) {
                    _sendVerification.postValue(client.body())
                } else {
                    val messageError = JSONObject(client.errorBody()!!.charStream().readText())
                    _sendVerification.postValue(
                        CodeVerificationResponse(
                            code = messageError.getInt("code"),
                            messages = messageError.getString("messages")
                        )
                    )
                }
            } catch (ex: Exception) {
                Timber.d(ex.toString())
            }
        }
    }

    fun resendVerification(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = useCase.resendUserVerification(email)
                if (client.isSuccessful) {
                    _resendVerification.postValue(client.body())
                } else {
                    val messageError = JSONObject(client.errorBody()!!.charStream().readText())
                    _resendVerification.postValue(
                        CodeVerificationResponse(
                            code = messageError.getInt("code"),
                            messages = messageError.getString("messages")
                        )
                    )
                }
            } catch (ex: Exception) {
                Timber.d(ex.toString())
            }
        }
    }
}