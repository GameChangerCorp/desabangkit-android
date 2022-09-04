package id.buildindo.desabangkit.android.core.utils

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable

object SendBundleData {

    fun Intent.sendIntentExtra(key: String, objectClass: Parcelable?) = putExtra(key, objectClass)

    fun Bundle.sendBundleExtra(key: String, objectClass: Parcelable?) = putParcelable(key, objectClass)

    inline fun <reified T> Intent.getIntentExtra(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    inline fun <reified T> Bundle.getBundleExtra(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }
}