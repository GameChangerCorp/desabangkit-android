package id.buildindo.desabangkit.android.ui.pages

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import id.buildindo.desabangkit.android.core.data.local.datastore.DataStorePreference
import id.buildindo.desabangkit.android.databinding.ActivitySplashScreenBinding
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.PreferenceViewModelFactory

class SplashScreenActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var _binding: ActivitySplashScreenBinding

    private lateinit var _viewModelDataStore : DatastoreViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()

        val pref = DataStorePreference.getInstance(dataStore)
        _viewModelDataStore = ViewModelProvider(this, PreferenceViewModelFactory(pref))[DatastoreViewModel::class.java]


        Handler(Looper.getMainLooper()).postDelayed({
            _viewModelDataStore.getLoginState().observe(this) { stateSaved ->
                if (stateSaved) {
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            finish()
        }, 4000)
    }

}