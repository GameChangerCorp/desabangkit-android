package id.buildindo.desabangkit.android.core.data.remote.response.roles

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("_id")
    val id: String? = null,
    val rolename : String? = null,
    val rolelabel : String? = null,
    val description : String? = null
)
