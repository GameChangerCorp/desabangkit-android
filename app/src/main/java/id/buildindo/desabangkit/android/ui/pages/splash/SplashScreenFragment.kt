package id.buildindo.desabangkit.android.ui.pages.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.utils.Navigation.moveToActivity
import id.buildindo.desabangkit.android.core.utils.Navigation.moveToDashboard
import id.buildindo.desabangkit.android.databinding.FragmentSplashScreenBinding
import id.buildindo.desabangkit.android.ui.pages.auth.LoginActivity
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private lateinit var _binding: FragmentSplashScreenBinding
    private lateinit var _viewModelDataStore: DatastoreViewModel
    private var _loginState = false
    private var _onboardState = false
    private var _saveRoles = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        _viewModelDataStore = ViewModelProvider(
            this
        )[DatastoreViewModel::class.java]
        observeDataStore()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if (_loginState) {
                moveToDashboard(_saveRoles, requireActivity(), requireContext())
            } else {
                if (_onboardState) {
                  moveToActivity<LoginActivity>(requireActivity(), requireContext())
                } else {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_onboardingFragment)
                }
            }
        }, 3000)
    }

    private fun observeDataStore() {
        _viewModelDataStore.getLoginState().observe(viewLifecycleOwner) { stateSaved -> _loginState = stateSaved }
        _viewModelDataStore.getOnBoardingState().observe(viewLifecycleOwner) { onBoardingSave -> _onboardState = onBoardingSave }
        _viewModelDataStore.getUserRoles().observe(viewLifecycleOwner) { rolesSaved -> _saveRoles = rolesSaved }
    }
}