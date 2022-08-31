package id.buildindo.desabangkit.android.ui.pages.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.databinding.ActivitySplashScreenBinding
import timber.log.Timber

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        supportActionBar?.hide()
    }
}