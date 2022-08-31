package id.buildindo.desabangkit.android.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import id.buildindo.desabangkit.android.ui.pages.auth.LoginActivity
import id.buildindo.desabangkit.android.ui.pages.customer.CustomerDashboardActivity
import id.buildindo.desabangkit.android.ui.pages.partner.PartnerDashboardActivity


object Navigation {
    fun finishActivity(activity: Activity) = activity.finish()
    fun movePagesFragment(fragment: Fragment, id: Int, bundle: Bundle? = null) =
        fragment.view?.findNavController()?.navigate(id, bundle)

    inline fun <reified T> moveToActivity(activity: Activity, context: Context) {
        val startIntent = Intent(context, T::class.java)
        activity.startActivity(startIntent)
        activity.finish()
    }

    fun moveToDashboard(roles: String, activity: Activity, context: Context) {
        when (roles) {
            Constant.Roles.PPN -> moveToActivity<PartnerDashboardActivity>(activity, context)
            Constant.Roles.CUSTOMER -> moveToActivity<CustomerDashboardActivity>(activity, context)
        }
    }

    fun openEmailApplication(activity: Activity) {
        val startIntent = Intent(Intent.ACTION_MAIN)
        startIntent.addCategory(Intent.CATEGORY_APP_EMAIL)
        activity.startActivity(startIntent)
        activity.finish()
    }
}