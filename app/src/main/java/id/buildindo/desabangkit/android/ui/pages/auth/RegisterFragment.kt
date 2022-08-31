package id.buildindo.desabangkit.android.ui.pages.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.InputChecker.checkEmail
import id.buildindo.desabangkit.android.core.utils.InputChecker.checkFullName
import id.buildindo.desabangkit.android.core.utils.InputChecker.checkPassword
import id.buildindo.desabangkit.android.core.utils.InteractionUtils.hideKeyboard
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.Navigation.finishActivity
import id.buildindo.desabangkit.android.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var _binding: FragmentRegisterBinding
    private var _name = ""
    private var _email = ""
    private var _password = ""
    private val _bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeBinding()
        getBundleData()
        _binding.edtFullname.setText(_name)
        _binding.edtEmail.setText(_email)
        _binding.edtPassword.setText(_password)
    }

    private fun initializeBinding() {
        with(_binding) {
            btnRegister.setOnClickListener {
                moveToRolesPagesWithData()
                hideKeyboard()
            }

            btnLogin.setOnClickListener {
                activity?.let { finish -> finishActivity(finish) }
            }

            ivBack.setOnClickListener {
                activity?.let { finish -> finishActivity(finish) }
            }
        }
    }

    private fun moveToRolesPagesWithData() {
        with(_binding) {
            _name = edtFullname.text.toString().trim()
            _email = edtEmail.text.toString().trim()
            _password = edtPassword.text.toString().trim()

            checkFullName(_name, tilFullname)
            checkEmail(_email, tilEmail)
            checkPassword(_password, tilPassword)

            if (checkFullName(_name, tilFullname) && checkEmail(_email, tilEmail) && checkPassword(_password, tilPassword)) {
                sendBundleData(_name, _email, _password)
                Navigation.movePagesFragment(requireParentFragment(), R.id.action_registerFragment_to_chooseRolesFragment, _bundle)
            }
        }
    }

    private fun sendBundleData(fullName: String, email: String, password: String) {
        _bundle.putString(Constant.GetIntentType.FULL_NAME, fullName)
        _bundle.putString(Constant.GetIntentType.EMAIL, email)
        _bundle.putString(Constant.GetIntentType.PASSWORD, password)
    }

    private fun getBundleData() {
        _name = arguments?.getString(Constant.GetIntentType.FULL_NAME) ?: ""
        _email = arguments?.getString(Constant.GetIntentType.EMAIL) ?: ""
        _password = arguments?.getString(Constant.GetIntentType.PASSWORD) ?: ""
    }
}