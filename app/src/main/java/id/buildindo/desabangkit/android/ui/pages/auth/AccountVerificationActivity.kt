package id.buildindo.desabangkit.android.ui.pages.auth

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.data.remote.response.login.LoginRequest
import id.buildindo.desabangkit.android.core.data.remote.response.login.Results
import id.buildindo.desabangkit.android.core.utils.AlertMessage
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.Navigation.moveToDashboard
import id.buildindo.desabangkit.android.core.utils.StringFormat.getLastUrlString
import id.buildindo.desabangkit.android.core.utils.ViewVisibility.showLoading
import id.buildindo.desabangkit.android.databinding.ActivityAccountVerificationBinding
import id.buildindo.desabangkit.android.ui.viewmodel.AuthViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel

@AndroidEntryPoint
class AccountVerificationActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityAccountVerificationBinding
    private val _viewModel: AuthViewModel by viewModels()
    private lateinit var _viewModelDataStore: DatastoreViewModel
    private var _email = ""
    private var _password = ""
    private var _verificationCode = ""
    private var _roles = ""
    private var _appLinkData: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAccountVerificationBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _viewModelDataStore = ViewModelProvider(this)[DatastoreViewModel::class.java]

        observeData()
        observeDataStore()

        supportActionBar?.hide()

        _appLinkData = intent.data
        readLink(_appLinkData.toString())

        _binding.btnGoToEmail.setOnClickListener {
            Navigation.openEmailApplication(this)
        }

        _binding.btnResendVerification.setOnClickListener {
            showLoading(true, _binding.progressBar)
            _viewModel.resendVerification(_email)
        }
    }


    private fun readLink(url: String) {
        when {
            url.contains(Constant.BaseUrl.URL_VERIFICATION_ACCOUNT) -> {
                _verificationCode = getLastUrlString(url, 8)
                _viewModel.sendVerification(_verificationCode)
                showLoading(true, _binding.progressBar)
            }
        }
    }

    private fun observeData() {
        _viewModel.sendVerification.observe(this) {
            Snackbar.make(_binding.root, it.messages.toString(), Snackbar.LENGTH_SHORT).show()
            if (it.messages == Constant.ResponseMessage.SUCCESS_VERIFICATION) {
                _viewModel.loginUser(
                    LoginRequest(
                        _email, _password
                    )
                )
            }
        }

        _viewModel.loginResponse.observe(this) {
            if (it != null) {
                showLoading(false, _binding.progressBar)
                saveAuthData(it.result)
                AlertMessage.showSnackbarNoAction(_binding.root, it.messages.toString(), 5000)
            }
        }

        _viewModel.resendVerification.observe(this) {
            if (it != null) {
                showLoading(false, _binding.progressBar)
                if (it.code == Constant.ResponseCode.SUCCESS) {
                    AlertMessage.showSnackbarToEmailApp(
                        _binding.root,
                        it.messages.toString(),
                        5000,
                        resources.getString(R.string.open_email),
                        this
                    )
                } else {
                    AlertMessage.showSnackbarNoAction(_binding.root, it.messages.toString(), 5000)
                }
            }
        }
    }

    private fun observeDataStore() {
        _viewModelDataStore.getUserEmail().observe(this) { _email = it }
        _viewModelDataStore.getUserPassword().observe(this) { _password = it }
    }

    private fun saveAuthData(results: Results?) {
        if (results != null) {
            _viewModelDataStore.apply {
                _roles = results.account?.role?.get(0)?.rolelabel!!
                _viewModelDataStore.saveLoginState(true)
                _viewModelDataStore.saveBearerToken(results.token!!)
                _viewModelDataStore.saveUsername(results.account.fullname!!)
                _viewModelDataStore.saveUserId(results.account.id!!)
                _viewModelDataStore.saveUserRoles(_roles)
            }
            moveToDashboard(_roles, this, this)
        }
    }
}