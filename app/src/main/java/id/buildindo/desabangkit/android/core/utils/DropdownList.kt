package id.buildindo.desabangkit.android.core.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

object DropdownList {
    fun createDropdownExposed(
        context: Context,
        layout: Int,
        dropdownRemoteData: ArrayList<String>,
        placeholder: AutoCompleteTextView
    ) {
        val adapter = ArrayAdapter(context, layout, dropdownRemoteData)
        placeholder.setAdapter(adapter)
    }
}