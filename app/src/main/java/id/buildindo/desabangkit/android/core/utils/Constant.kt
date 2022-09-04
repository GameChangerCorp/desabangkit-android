package id.buildindo.desabangkit.android.core.utils

class Constant {
    object GetIntentType {
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val FULL_NAME = "full_name"
        const val REGISTER_DATA = "registerData"
    }

    object Roles {
        const val PPN = "PPN"
        const val PARTNER = "Partner"
        const val KONSUMEN = "Konsumen"
        const val CUSTOMER = "Customer"
    }

    object ResponseCode {
        const val SUCCESS = 200
        const val FAILED = 400
        const val SERVER_ERROR = 500
    }

    object ResponseMessage {
        const val SUCCESS_VERIFICATION = "success verification account"
        const val NEED_VERIFICATION = "need to verification email"
    }

    object BaseUrl {
        const val URL_VERIFICATION_ACCOUNT = "api-desabangkit.herokuapp.com/users/verification-account"
    }

    object Pages {
        const val SUPPLY_PRODUCT_PAGES = "SupplyProductPages"
        const val DETAIL_PRODUCT_PAGES = "DetailProductPages"
        const val CUSTOMER_BERANDA_PAGES = "CustomerBerandaPages"
    }

    object BundleName {
        const val OPEN_PAGES_FROM = "openFrom"
    }
}