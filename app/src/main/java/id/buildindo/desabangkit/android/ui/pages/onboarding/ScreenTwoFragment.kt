package id.buildindo.desabangkit.android.ui.pages.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.data.local.datastore.DataStorePreference
import id.buildindo.desabangkit.android.databinding.FragmentScreenTwoBinding
import id.buildindo.desabangkit.android.ui.pages.LoginActivity
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.PreferenceViewModelFactory


class ScreenTwoFragment : Fragment() {
    private lateinit var _binding : FragmentScreenTwoBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var _viewModelDataStore : DatastoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenTwoBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = DataStorePreference.getInstance(requireContext().dataStore)
        _viewModelDataStore = ViewModelProvider(this, PreferenceViewModelFactory(pref))[DatastoreViewModel::class.java]

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager2)
        with(_binding) {
            btnNext.setOnClickListener {
                viewPager?.currentItem = 2
            }

            tvLewati.setOnClickListener {
                val moveToHome = Intent(context, LoginActivity::class.java)
                startActivity(moveToHome)
                _viewModelDataStore.saveOnBoardingState(true)
                activity?.finish()
            }
        }
    }
}