package id.buildindo.desabangkit.android.ui.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.core.data.ApiResponse
import id.buildindo.desabangkit.android.core.data.Status
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.utils.InteractionUtils.hideKeyboard
import id.buildindo.desabangkit.android.databinding.ActivityRegisterBinding
import id.buildindo.desabangkit.android.ui.viewmodel.AuthViewModel

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityRegisterBinding
    private val _viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.hide()

        _binding.btnRegister.setOnClickListener {
            signup()
            hideKeyboard()
        }
        _binding.btnLogin.setOnClickListener {
            moveToLoginActivity()
        }
        _viewModel.registerResponse.observe(this@RegisterActivity) {
            _binding.btnRegister.isClickable = true
            _binding.btnRegister.isActivated = true
            when(it.code){
                200 -> {
                    moveToLoginActivity()
                    Snackbar.make(_binding.root, it.messages.toString(), Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
                400 -> {
                    Snackbar.make(_binding.root, it.messages.toString(), Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun moveToLoginActivity() {
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun signup() {
        _binding.apply {
            val name = edtFullname.text.toString().trim()
            val nameCondition: Boolean
            val email = edtEmail.text.toString().trim()
            val emailCondition: Boolean
            val password = edtPassword.text.toString().trim()
            val passwordCondition: Boolean
            val rePassword = edtRePassword.text.toString().trim()

            when {
                name.isEmpty() -> {
                    tilFullname.error = "Silahkan masukkan nama Anda terlebih dahulu."
                    nameCondition = false
                }
                else -> {
                    tilFullname.error = null
                    nameCondition = true
                }
            }

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
                password.isEmpty() && rePassword.isEmpty() -> {
                    tilPassword.error = "Silahkan masukkan password Anda terlebih dahulu."
                    tilRePassword.error = "Jangan lupa ketik ulang password Anda."
                    passwordCondition = false
                }
                rePassword.isEmpty() -> {
                    tilRePassword.error = "Jangan lupa ketik ulang password Anda"
                    passwordCondition = false
                }
                password.length < 6 && password.length < 6 -> {
                    tilPassword.error = "Password harus lebih dari 6 Huruf"
                    tilRePassword.error = "Jumlah Password tidak sama"
                    passwordCondition = false
                }
                rePassword.length < 6 -> {
                    tilRePassword.error = "Jumlah Password tidak sama"
                    passwordCondition = false
                }
                password != rePassword -> {
                    tilRePassword.error = "Password tidak sama"
                    passwordCondition = false
                }
                else -> {
                    tilPassword.error = null
                    tilRePassword.error = null
                    passwordCondition = true
                }
            }

            if (emailCondition && nameCondition && passwordCondition) {
                btnRegister.isClickable = false
                btnRegister.isActivated = false
                _viewModel.registerUser(
                    RegisterRequest(name, email, password, 1)
                )
                showLoading(true)
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) _binding.progressBar.visibility =
            View.VISIBLE else _binding.progressBar.visibility = View.GONE
    }
}