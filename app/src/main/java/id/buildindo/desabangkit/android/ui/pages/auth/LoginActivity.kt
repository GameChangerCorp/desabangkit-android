package id.buildindo.desabangkit.android.ui.pages.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.utils.AlertMessage
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.InputChecker
import id.buildindo.desabangkit.android.core.utils.InteractionUtils.hideKeyboard
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.ViewVisibility.showLoading
import id.buildindo.desabangkit.android.databinding.ActivityLoginBinding
import id.buildindo.desabangkit.android.ui.viewmodel.AuthViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.core.data.remote.response.login.Results as LoginResults

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityLoginBinding
    private val _viewModel: AuthViewModel by viewModels()
    private lateinit var _viewModelDataStore: DatastoreViewModel

    private var _email = ""
    private var _password = ""
    private var _roles = ""

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

        _viewModel.loginResponse.observe(this@LoginActivity) {
            if (it.code == Constant.ResponseCode.SUCCESS) {
                showLoading(false, _binding.progressBar)
                saveAuthData(it.result)
                AlertMessage.showSnackbarNoAction(_binding.root, it.messages.toString(), 5000)
            } else {
                if (it.messages.toString() == Constant.ResponseMessage.NEED_VERIFICATION) {
                    intent = Intent(this, AccountVerificationActivity::class.java)
                    _viewModelDataStore.saveUserEmail(_email)
                    _viewModelDataStore.saveUserPassword(_password)
                    startActivity(intent)
                }
                showLoading(false, _binding.progressBar)
                AlertMessage.showSnackbarNoAction(_binding.root, it.messages.toString(), 5000)
            }
        }
    }

    private fun login() {
        _binding.apply {
            _email = edtEmail.text.toString().trim()
            _password = edtPassword.text.toString().trim()

            InputChecker.checkEmail(_email, tilEmail)
            InputChecker.checkPassword(_password, tilPassword)

            if (InputChecker.checkEmail(_email, tilEmail) && InputChecker.checkPassword(
                    _password,
                    tilPassword
                )
            ) {
                _viewModel.loginUser(LoginRequest(_email, _password))
                showLoading(true, _binding.progressBar)
            }
        }
    }

    private fun saveAuthData(results: LoginResults?) {
        if (results != null) {
            _viewModelDataStore.apply {
                _roles = results.account?.role?.get(0)?.rolelabel!!
                _viewModelDataStore.saveLoginState(true)
                _viewModelDataStore.saveBearerToken(results.token!!)
                _viewModelDataStore.saveUsername(results.account.fullname!!)
                _viewModelDataStore.saveUserId(results.account.id!!)
                _viewModelDataStore.saveUserEmail(results.account.email!!)
                _viewModelDataStore.saveUserRoles(_roles)
            }
            Navigation.moveToDashboard(_roles, this, this)
        }
    }
}