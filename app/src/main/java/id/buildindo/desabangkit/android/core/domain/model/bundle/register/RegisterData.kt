package id.buildindo.desabangkit.android.core.domain.model.bundle.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterData(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val chooseRoles: String = ""
): Parcelable