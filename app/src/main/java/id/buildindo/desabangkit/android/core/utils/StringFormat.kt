package id.buildindo.desabangkit.android.core.utils

object StringFormat {
    fun currencyFormat(double: String?, unit: String? = "") : String {
        return "Rp. ${double?.substring(0, double.length - 3)}/$unit"
    }

    fun currencyFormatNoUnit(double: String?) : String {
        return "Rp. ${double?.substring(0, double.length - 3)}"
    }

    fun getLastUrlString(url: String, length: Int): String {
        return url.substring(url.length - length, url.length)
    }
}