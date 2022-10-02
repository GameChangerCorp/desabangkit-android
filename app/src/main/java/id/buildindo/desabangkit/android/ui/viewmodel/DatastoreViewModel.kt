package id.buildindo.desabangkit.android.ui.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.buildindo.desabangkit.android.core.data.local.datastore.DataStoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatastoreViewModel @Inject constructor(private val pref: DataStoreRepository) : ViewModel() {
    fun getLoginState(): LiveData<Boolean> = pref.getLoginState().asLiveData()
    fun getBearerToken(): LiveData<String> = pref.getBearerToken().asLiveData()
    fun getUsername(): LiveData<String> = pref.getUserName().asLiveData()
    fun getOnBoardingState(): LiveData<Boolean> = pref.getOnBoardingState().asLiveData()
    fun getUserRoles(): LiveData<String> = pref.getUserRoles().asLiveData()
    fun getUserEmail(): LiveData<String> = pref.getUserEmail().asLiveData()
    fun getUserPassword(): LiveData<String> = pref.getUserPassword().asLiveData()
    fun getUserId(): LiveData<String> = pref.getUserId().asLiveData()

    fun saveLoginState(loginState: Boolean) = viewModelScope.launch {
        pref.saveLoginState(loginState)
    }

    fun saveBearerToken(bearerKey: String) = viewModelScope.launch {
        pref.saveBearerToken(bearerKey)
    }

    fun saveUsername(username: String) = viewModelScope.launch {
        pref.saveUserName(username)
    }

    fun saveOnBoardingState(state: Boolean) = viewModelScope.launch {
        pref.saveOnBoardingState(state)
    }

    fun saveUserRoles(roles: String) = viewModelScope.launch {
        pref.saveUserRoles(roles)
    }

    fun saveUserEmail(email: String) = viewModelScope.launch {
        pref.saveUserEmail(email)
    }

    fun saveUserPassword(password: String) = viewModelScope.launch {
        pref.saveUserPassword(password)
    }

    fun saveUserId(id: String) = viewModelScope.launch {
        pref.saveUserId(id)
    }
}