package id.buildindo.desabangkit.android.ui.pages.partner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.databinding.FragmentAccountPartnerBinding
import id.buildindo.desabangkit.android.ui.pages.auth.LoginActivity
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel

@AndroidEntryPoint
class AccountPartnerFragment : Fragment() {

    private lateinit var _binding : FragmentAccountPartnerBinding
    private lateinit var _viewModelDataStore : DatastoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountPartnerBinding.inflate(layoutInflater, container, false)
        initializeViewModel()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.btnLogout.setOnClickListener {
            _viewModelDataStore.saveLoginState(false)
            _viewModelDataStore.saveBearerToken("")
            _viewModelDataStore.saveUserRoles("")
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }


    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this)[DatastoreViewModel::class.java]
    }

}