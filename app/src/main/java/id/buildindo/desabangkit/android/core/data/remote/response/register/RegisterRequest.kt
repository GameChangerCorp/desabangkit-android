package id.buildindo.desabangkit.android.core.data.remote.response.register

data class RegisterRequest(
    val fullname: String? = "",
    val email: String? = "",
    val password: String? = "",
    val role_id : String? = ""
)
