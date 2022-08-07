package id.buildindo.desabangkit.android.core.data.remote.response.login

data class Account(
    val id : Int? = 0,
    val fullname: String? = "",
    val email: String? = "",
    val password: String? = "",
    val id_role: Int? = 0,
    val role : Role? = null,
    val urlPhoto: String? = ""
)
