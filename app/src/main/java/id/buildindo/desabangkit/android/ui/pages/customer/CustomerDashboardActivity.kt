package id.buildindo.desabangkit.android.ui.pages.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.databinding.ActivityCustomerDashboardBinding

@AndroidEntryPoint
class CustomerDashboardActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityCustomerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCustomerDashboardBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()
        createBottomNavigation()
    }

    private fun createBottomNavigation() {
        _binding.apply {
            val navController = findNavController(R.id.nav_host_fragment)

            val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_beranda, R.id.navigation_transaction, R.id.navigation_account
            ).build()

            navController.addOnDestinationChangedListener{ _, destination, _ ->
                when(destination.id){
                    R.id.navigation_beranda -> navView.visibility = View.VISIBLE
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