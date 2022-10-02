package id.buildindo.desabangkit.android.core.domain.model.bundle.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputProductData(
    val customerId: String = "",
    val productPhoto: String = "",
    val productName: String = "",
    val productCategory: String = "",
    val productPrice: String = "",
    val productQuantity: String = "",
    val productDeliveryOption: String = "",
    val address: String = "",
    val cooperationId: String = "",
    val cooperationName: String = "",
    val isPreorder: Boolean? = null
):Parcelable
