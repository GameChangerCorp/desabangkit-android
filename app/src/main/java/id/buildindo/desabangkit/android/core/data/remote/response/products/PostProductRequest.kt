package id.buildindo.desabangkit.android.core.data.remote.response.products

data class PostProductRequest(
    val photo_url : String,
    val name : String? = null,
    val price_expected : Int? = null,
    val quantity : Int? = null,
    val category : String? = null,
    val deliver_option : String? = null,
    val cooperationId : String? = null,
    val userid : String? = null,
    val useraddress : String? = null,
)
