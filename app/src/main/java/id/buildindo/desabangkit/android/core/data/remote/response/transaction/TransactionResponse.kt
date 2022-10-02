package id.buildindo.desabangkit.android.core.data.remote.response.transaction

data class TransactionResponse(
    val code: Int? = 0,
    val messages: String? = "",
    val result: List<TransactionDTO>? = null
)

data class TransactionDTO(
    val status: String? = "",
    val productid: String? = "",
    val product: List<TransactionData>? = null,
    val userid: String? = "",
    val account: List<AccountData>? = null,
    val amount: Int? = 0,
    val created_at: String? = ""
)

data class TransactionData(
    val id: String? = "",
    val sku: String? = "",
    val photo_url: String? = "",
    val name: String? = "",
    val price: Int? = 0,
    val quantity: Int? = 0,
    val category: String? = "",
    val delivery_option: String? = "",
    val cooperationid: String? = "",
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val userid: String? = "",
    val user_address: String? = "",
    val is_preorder: Boolean? = false,
    val is_approved: Boolean? = false,
    val created_at: String? = ""
)

data class AccountData(
    val ID: String? = "",
    val Fullname: String? = "",
    val Email: String? = "",
    val Password: String? = "",
    val Role_id: String? = "",
    val IsVerified: Boolean? = false,
    val roles: String? = "",
)

