package id.buildindo.desabangkit.android.ui.pages.partner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.databinding.FragmentPartnerBerandaBinding
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel

@AndroidEntryPoint
class PartnerBerandaFragment : Fragment() {

    private lateinit var _dataStoreViewModel : DatastoreViewModel
    private lateinit var _binding : FragmentPartnerBerandaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartnerBerandaBinding.inflate(inflater, container, false)
        initializeViewModel()
        observeDataStore()
        return _binding.root
    }

    private fun observeDataStore() {
        _dataStoreViewModel.getUsername().observe(viewLifecycleOwner) { name ->
            _binding.usernameText.text = name
        }
    }

    private fun initializeViewModel() {
        _dataStoreViewModel = ViewModelProvider(requireActivity())[DatastoreViewModel::class.java]
    }
}