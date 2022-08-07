package id.buildindo.desabangkit.android.ui.viewmodel

import androidx.lifecycle.*
import id.buildindo.desabangkit.android.core.data.local.datastore.DataStorePreference
import kotlinx.coroutines.launch

class DatastoreViewModel(private val pref: DataStorePreference) : ViewModel() {
    fun getLoginState(): LiveData<Boolean> {
        return pref.getLoginState().asLiveData()
    }

    fun saveLoginState(loginState: Boolean){
        viewModelScope.launch {
            pref.saveLoginState(loginState)
        }
    }

    fun getBearerToken() : LiveData<String>{
        return pref.getBearerToken().asLiveData()
    }

    fun saveBearerToken(bearerKey: String){
        viewModelScope.launch {
            pref.saveBearerToken(bearerKey)
        }
    }

    fun getUsername () : LiveData <String>{
        return pref.getUserName().asLiveData()
    }

    fun saveUsername (username : String){
        viewModelScope.launch {
            pref.saveUserName(username)
        }
    }
}

class PreferenceViewModelFactory(private val pref: DataStorePreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatastoreViewModel::class.java)) {
            return DatastoreViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class: " + modelClass.name)
    }
}