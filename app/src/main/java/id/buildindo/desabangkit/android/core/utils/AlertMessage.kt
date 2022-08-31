package id.buildindo.desabangkit.android.core.utils

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar

object AlertMessage {
    fun showSnackbarNoAction(view: View, message: String, duration: Int) {
        Snackbar.make(view, message, duration).show()
    }

    fun showSnackbarToEmailApp(view: View, message: String, duration: Int, actionMessage: String, activity: Activity) {
        Snackbar.make(view, message, duration).setAction(actionMessage){
            Navigation.openEmailApplication(activity)
        }.show()
    }
}