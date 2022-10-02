package id.buildindo.desabangkit.android.core.utils

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import java.util.*

object StringFormat {
    fun getLastUrlString(url: String, length: Int): String {
        return url.substring(url.length - length, url.length)
    }

    fun getIndonesianCurrencyFormat(value: Long): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(value)
    }

    fun currencyFormat(double: String?, unit: String? = "") : String {
        return "Rp. ${double?.substring(0, double.length - 3)}/$unit"
    }

    fun dateToIndonesianWIB(date: String): String {
        val dateSplit = date.split("T")
        val dateSplit2 = dateSplit[0].split("-")
        return "${dateSplit2[2]}-${dateSplit2[1]}-${dateSplit2[0]} ${dateSplit[1]}"
    }

    fun formatDateToIndonesian(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return formatter.format(parser.parse(date))
    }


}