package id.buildindo.desabangkit.android.core.data

sealed class ApiResponse<out T>{
    data class Success<out R>(val data: R) : ApiResponse<R>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    data class Loading(val isLoading: Boolean) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}
