package id.buildindo.desabangkit.android.ui.pages.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.utils.InteractionUtils.hideKeyboard
import id.buildindo.desabangkit.android.databinding.ActivityLoginBinding
import id.buildindo.desabangkit.android.ui.pages.CustomerDashboardActivity
import id.buildindo.desabangkit.android.ui.pages.partner.PartnerDashboardActivity
import id.buildindo.desabangkit.android.ui.viewmodel.AuthViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityLoginBinding
    private val _viewModel: AuthViewModel by viewModels()
    private lateinit var _viewModelDataStore : DatastoreViewModel

    private var _email = ""
    private var _password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()

        _viewModelDataStore = ViewModelProvider(this)[DatastoreViewModel::class.java]

        _binding.btnRegister.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        _binding.btnLogin.setOnClickListener {
            login()
            hideKeyboard()
        }

        _viewModel.loginResponse.observe(this@LoginActivity){
            Timber.d("cek dapet datanya ga ya coy $it")
            if (it.code == 200){
                showLoading(false)
                _viewModelDataStore.saveLoginState(true)
                _viewModelDataStore.saveBearerToken(it.result?.token!!)
                _viewModelDataStore.saveUsername(it.result.account?.fullname!!)
                val roles = it.result.account.role?.get(0)?.rolelabel!!
                _viewModelDataStore.saveUserRoles(roles)
                moveToDashboard(roles)
                Snackbar.make(_binding.root, it.messages.toString(), Toast.LENGTH_SHORT).show()
            }else{
                if (it.messages.toString() == "need to verification email"){
                    intent = Intent(this, AccountVerificationActivity::class.java)
                        .putExtra("email", _email)
                        .putExtra("password", _password)
                    startActivity(intent)
                }
                showLoading(false)
                Snackbar.make(_binding.root, it.messages.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun moveToDashboard(roles: String){
        when(roles){
            "PPN" -> {
                moveToActivity<PartnerDashboardActivity>()
            }
            "Customer" -> {
                moveToActivity<CustomerDashboardActivity>()
            }
        }
    }

    private inline fun <reified T> moveToActivity() {
        intent = Intent(this, T::class.java)
        startActivity(intent)
        finish()
    }

    private fun login(){
        _binding.apply {
            _email = edtEmail.text.toString().trim()
            val emailCondition: Boolean
            _password = edtPassword.text.toString().trim()
            val passwordCondition: Boolean

            when {
                _email.isEmpty() -> {
                    tilEmail.error = "Silahkan masukkan email Anda terlebih dahulu."
                    emailCondition = false
                }
                !Patterns.EMAIL_ADDRESS.matcher(_email).matches() -> {
                    tilEmail.error = "Email Anda tidak valid."
                    emailCondition = false
                }
                else -> {
                    tilEmail.error = null
                    emailCondition = true
                }
            }

            when {
                _password.isEmpty() -> {
                    tilPassword.error = "Silahkan masukkan password Anda terlebih dahulu."
                    passwordCondition = false
                }
                _password.length < 6 -> {
                    tilPassword.error = "Password harus lebih dari 6 Huruf"
                    passwordCondition = false
                }
                else -> {
                    tilPassword.error = null
                    passwordCondition = true
                }
            }

            if (emailCondition && passwordCondition){
                btnLogin.isClickable = false
                btnLogin.isActivated = false
                _viewModel.loginUser(
                    LoginRequest(_email, _password)
                )
                showLoading(true)
            }
        }
    }

    private fun showLoading(loading: Boolean){
        if (loading) _binding.progressBar.visibility = View.VISIBLE else _binding.progressBar.visibility = View.GONE
    }
}