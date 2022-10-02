package id.buildindo.desabangkit.android.core.data.remote.response.cooperation

data class CooperationResponse(
    val code: Int? = 0,
    val message: String? = "",
    val data: CooperationDTO? = null,
)

data class CooperationDTO(
    val id: String? = "",
    val name: String? = "",
    val address: AddressDTO? = null,
    val memberUid: String? = ""
)

data class AddressDTO(
    val street: String? = "",
    val latitude: String? = "",
    val longitude: String? = "",
    val village: String? = "",
    val district: String? = "",
    val city: String? = "",
    val province: String? = "",
)
