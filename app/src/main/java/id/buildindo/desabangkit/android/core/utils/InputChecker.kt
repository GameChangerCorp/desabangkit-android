package id.buildindo.desabangkit.android.core.utils

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import id.buildindo.desabangkit.android.R

object InputChecker {

    fun checkFullName(name: String, inputText: TextInputLayout): Boolean {
        var nameCondition: Boolean
        inputText.apply {
            when {
                name.isEmpty() -> {
                    error = resources.getString(R.string.error_name_text_empty)
                    isErrorEnabled = true
                    nameCondition = false
                }
                else -> {
                    error = null
                    isErrorEnabled = false
                    nameCondition = true
                }
            }
        }

        return nameCondition
    }

    fun checkEmail(email: String, inputText: TextInputLayout): Boolean {
        var emailCondition: Boolean
        inputText.apply {
            when {
                email.isEmpty() -> {
                    error = resources.getString(R.string.error_email_text_empty)
                    isErrorEnabled = true
                    emailCondition = false
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    error = resources.getString(R.string.error_email_text_not_matches)
                    isErrorEnabled = true
                    emailCondition = false
                }
                else -> {
                    error = null
                    isErrorEnabled = false
                    emailCondition = true
                }
            }
        }
        return emailCondition
    }


    fun checkPassword(password: String, inputText: TextInputLayout): Boolean {
        var passwordCondition: Boolean
        inputText.apply {
            when {
                password.isEmpty() -> {
                    error = resources.getString(R.string.error_password_text_empty)
                    isErrorEnabled = true
                    passwordCondition = false
                }

                password.length < 6 -> {
                    error = resources.getString(R.string.error_password_text_length)
                    isErrorEnabled = true
                    passwordCondition = false
                }
                else -> {
                    error = null
                    isErrorEnabled = false
                    passwordCondition = true
                }
            }
        }

        return passwordCondition
    }
}