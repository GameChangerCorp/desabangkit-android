package id.buildindo.desabangkit.android.core.data.remote.response.products

data class ProductHistoryResponse(
    val code: Int? = 0,
    val messages: String? = "",
    val result: List<ProductHistoryResults>? = null
)

data class ProductHistoryResults(
    val id: String? = "",
    val sku: String? = "",
    val photo_url: String? = "",
    val name: String? = "",
    val category: String? = "",
    val price_expected: Int? = 0,
    val Quantity: Int? = 0,
    val delivery_option: String? = "",
    val cooperationid: String? = "",
    val cooperations: List<CooperationResults>? = null,
    val userid: String? = "",
    val account: AccountResults? = null,
    val user_address: String? = "",
    val is_preorder: Boolean? = false,
    val is_approved: Boolean? = false,
    val created_at: String? = ""
)

data class CooperationResults(
    val id: String? = "",
    val name: String? = "",
    val address: String? = "",
    val city: String? = "",
    val province: String? = "",
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val photo_url: String? = "",
    val email: String? = "",
    val username: String? = "",
    val password: String? = "",
    val created_at: String? = ""
)

data class AccountResults(
    val ID: String? = "",
    val Email: String? = "",
    val Fullname: String? = "",
    val Password: String? = "",
    val Role_id: String? = "",
    val IsVerified: Boolean? = false,
    val roles: List<RolesResults>? = null
)

data class RolesResults(
    val _id: String? = "",
    val rolename: String? = "",
    val rolelabel: String? = "",
    val description: String? = ""
)