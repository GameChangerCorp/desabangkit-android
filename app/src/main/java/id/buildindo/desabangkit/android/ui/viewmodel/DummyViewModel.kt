package id.buildindo.desabangkit.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.buildindo.desabangkit.android.core.domain.model.Roles
import id.buildindo.desabangkit.android.core.utils.DataDummy
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor() : ViewModel() {

    fun getRolesData() : List<Roles> = DataDummy.getRolesData()
}