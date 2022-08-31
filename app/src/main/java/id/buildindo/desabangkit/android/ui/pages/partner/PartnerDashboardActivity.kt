package id.buildindo.desabangkit.android.ui.pages.partner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.databinding.ActivityPartnerDashboardBinding

@AndroidEntryPoint
class PartnerDashboardActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityPartnerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPartnerDashboardBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()
        createBottomNavigation()
    }

    private fun createBottomNavigation() {
        _binding.apply {
            val navController = findNavController(R.id.nav_host_fragment)

            val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_beranda, R.id.navigation_supply, R.id.navigation_transaction, R.id.navigation_account
            ).build()

            navController.addOnDestinationChangedListener{ _, destination, _ ->
                when(destination.id){
                    R.id.navigation_beranda -> navView.visibility = View.VISIBLE
                    R.id.navigation_supply -> navView.visibility = View.VISIBLE
                    R.id.navigation_transaction -> navView.visibility = View.VISIBLE
                    R.id.navigation_account -> navView.visibility = View.VISIBLE
                    else -> navView.visibility = View.GONE
                }
            }

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }
}