package id.buildindo.desabangkit.android.core.data.remote.response.login

data class LoginResponse(
    val code : Int? = 0,
    val messages: String? = "",
    val result : Results? = null
)
