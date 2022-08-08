package id.buildindo.desabangkit.android.core.data

sealed class Status<T>(val data : T? = null, val error : String? = null){
    class Success<T>(data: T?) : Status<T>(data)
    class Loading<T>() : Status<T>()
    class Error<T>(data: T, error: String) : Status<T>(data, error)
}
