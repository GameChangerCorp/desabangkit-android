package id.buildindo.desabangkit.android.core.data.remote.response.login

import com.google.gson.annotations.SerializedName
import id.buildindo.desabangkit.android.core.data.remote.response.roles.Results as Roles

data class Account(
    @SerializedName("ID")
    val id : String? = "",
    @SerializedName("Fullname")
    val fullname: String? = "",
    @SerializedName("Email")
    val email: String? = "",
    @SerializedName("Password")
    val password: String? = "",
    @SerializedName("Role_id")
    val id_role: String? = "",
    @SerializedName("roles")
    val role : List<Roles>? = null,
)
