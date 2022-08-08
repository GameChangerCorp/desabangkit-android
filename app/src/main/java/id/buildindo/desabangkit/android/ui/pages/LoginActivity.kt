package id.buildindo.desabangkit.android.ui.pages

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.core.data.local.datastore.DataStorePreference
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.utils.InteractionUtils.hideKeyboard
import id.buildindo.desabangkit.android.databinding.ActivityLoginBinding
import id.buildindo.desabangkit.android.ui.viewmodel.AuthViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.PreferenceViewModelFactory
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    private lateinit var _binding : ActivityLoginBinding
    private val _viewModel: AuthViewModel by viewModels()
    private lateinit var _viewModelDataStore : DatastoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()


        val pref = DataStorePreference.getInstance(dataStore)
        _viewModelDataStore = ViewModelProvider(this, PreferenceViewModelFactory(pref))[DatastoreViewModel::class.java]

        _binding.btnRegister.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        _binding.btnLogin.setOnClickListener {
            login()
            hideKeyboard()
        }

        _viewModel.loginResponse.observe(this@LoginActivity){
            if (it.code == 200){
                showLoading(false)
                _viewModelDataStore.saveLoginState(true)
                _viewModelDataStore.saveBearerToken(it.result?.token!!)
                _viewModelDataStore.saveUsername(it.result.account?.fullname!!)
                moveToMainActivity()
                Snackbar.make(_binding.root, it.messages.toString(), Toast.LENGTH_SHORT).show()
            }else{
                showLoading(false)
                Snackbar.make(_binding.root, it.messages.toString(), Toast.LENGTH_SHORT).show()
            }

            _binding.btnLogin.isClickable = true
            _binding.btnLogin.isActivated = true
        }
    }

    private fun moveToMainActivity(){
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login(){
        _binding.apply {
            val email = edtEmail.text.toString().trim()
            val emailCondition: Boolean
            val password = edtPassword.text.toString().trim()
            val passwordCondition: Boolean


            when {
                email.isEmpty() -> {
                    tilEmail.error = "Silahkan masukkan email Anda terlebih dahulu."
                    emailCondition = false
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    tilEmail.error = "Email Anda tidak valid."
                    emailCondition = false
                }
                else -> {
                    tilEmail.error = null
                    emailCondition = true
                }
            }

            when {
                password.isEmpty() -> {
                    tilPassword.error = "Silahkan masukkan password Anda terlebih dahulu."
                    passwordCondition = false
                }
                password.length < 6 -> {
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
                    LoginRequest(email, password)
                )
                showLoading(true)
            }
        }
    }

    private fun showLoading(loading: Boolean){
        if (loading) _binding.progressBar.visibility = View.VISIBLE else _binding.progressBar.visibility = View.GONE
    }
}