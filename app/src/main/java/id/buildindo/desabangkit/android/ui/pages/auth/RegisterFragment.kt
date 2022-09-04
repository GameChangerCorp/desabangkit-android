package id.buildindo.desabangkit.android.ui.pages.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.domain.model.bundle.register.RegisterData
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.InputChecker.checkEmail
import id.buildindo.desabangkit.android.core.utils.InputChecker.checkFullName
import id.buildindo.desabangkit.android.core.utils.InputChecker.checkPassword
import id.buildindo.desabangkit.android.core.utils.InteractionUtils.hideKeyboard
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.Navigation.finishActivity
import id.buildindo.desabangkit.android.core.utils.SendBundleData.getBundleExtra
import id.buildindo.desabangkit.android.core.utils.SendBundleData.sendBundleExtra
import id.buildindo.desabangkit.android.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var _binding: FragmentRegisterBinding
    private var _name = ""
    private var _email = ""
    private var _password = ""
    private val _bundle = Bundle()
    private var _registerData = RegisterData()

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
        _registerData =
            arguments?.getBundleExtra<RegisterData>(Constant.GetIntentType.REGISTER_DATA)!!
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

            edtFullname.setText(_registerData.name)
            edtEmail.setText(_registerData.email)
            edtPassword.setText(_registerData.password)

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
                _bundle.sendBundleExtra(
                    Constant.GetIntentType.REGISTER_DATA,
                    RegisterData(
                        name = _name,
                        email = _email,
                        password = _password
                    )
                )
                Navigation.movePagesFragment(requireParentFragment(), R.id.action_registerFragment_to_chooseRolesFragment, _bundle)
            }
        }
    }
}