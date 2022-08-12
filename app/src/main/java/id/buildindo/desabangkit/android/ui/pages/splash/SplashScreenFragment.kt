package id.buildindo.desabangkit.android.ui.pages.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.data.local.datastore.DataStorePreference
import id.buildindo.desabangkit.android.databinding.FragmentSplashScreenBinding
import id.buildindo.desabangkit.android.ui.pages.LoginActivity
import id.buildindo.desabangkit.android.ui.pages.MainActivity
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.PreferenceViewModelFactory
import timber.log.Timber


class SplashScreenFragment : Fragment() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var _binding : FragmentSplashScreenBinding
    private lateinit var _viewModelDataStore : DatastoreViewModel
    private var _loginState = false
    private var _onboardState = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        val pref = DataStorePreference.getInstance(requireContext().dataStore)
        _viewModelDataStore = ViewModelProvider(this, PreferenceViewModelFactory(pref))[DatastoreViewModel::class.java]
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeDataStore()

        Handler(Looper.getMainLooper()).postDelayed({
            if (_loginState) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }else{
                if (_onboardState){
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    findNavController().navigate(R.id.action_splashScreenFragment_to_onboardingFragment)
                }
            }
        }, 2000)
    }

    private fun observeDataStore(){
        _viewModelDataStore.getLoginState().observe(viewLifecycleOwner) { stateSaved ->
            _loginState = stateSaved
        }

        _viewModelDataStore.getOnBoardingState().observe(viewLifecycleOwner) { onBoardingSave ->
            _onboardState = onBoardingSave
            Timber.d("cek state onboard $_onboardState")
        }
    }

}