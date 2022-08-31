package id.buildindo.desabangkit.android.ui.pages.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.core.utils.Navigation.moveToActivity
import id.buildindo.desabangkit.android.databinding.FragmentCustomerAccountBinding
import id.buildindo.desabangkit.android.ui.pages.auth.LoginActivity
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel

@AndroidEntryPoint
class CustomerAccountFragment : Fragment() {

    private lateinit var _binding: FragmentCustomerAccountBinding
    private lateinit var _viewModelDataStore: DatastoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerAccountBinding.inflate(layoutInflater, container, false)
        initializeViewModel()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.btnLogout.setOnClickListener {
            observeDataStore()
            moveToActivity<LoginActivity>(requireActivity(), requireContext())
        }
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this)[DatastoreViewModel::class.java]
    }

    private fun observeDataStore() {
        _viewModelDataStore.apply {
            saveLoginState(false)
            saveUserRoles("")
            saveBearerToken("")
            saveUserId("")
            saveUsername("")
            moveToActivity<LoginActivity>(requireActivity(), requireContext())
        }
    }
}