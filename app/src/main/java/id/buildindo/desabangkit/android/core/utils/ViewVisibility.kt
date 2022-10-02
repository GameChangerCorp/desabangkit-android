package id.buildindo.desabangkit.android.core.utils

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

object ViewVisibility {
    fun showLoading(loading: Boolean, progressBar: ConstraintLayout) =
        if (loading) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE


    fun View.show() = View.VISIBLE

    fun View.remove() = View.GONE
}