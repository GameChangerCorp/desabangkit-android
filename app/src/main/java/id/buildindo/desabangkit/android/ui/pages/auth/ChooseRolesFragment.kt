package id.buildindo.desabangkit.android.ui.pages.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.data.remote.response.register.RegisterRequest
import id.buildindo.desabangkit.android.core.domain.model.bundle.register.RegisterData
import id.buildindo.desabangkit.android.core.utils.AlertMessage
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.SendBundleData.getBundleExtra
import id.buildindo.desabangkit.android.core.utils.SendBundleData.sendBundleExtra
import id.buildindo.desabangkit.android.core.utils.ViewVisibility.showLoading
import id.buildindo.desabangkit.android.databinding.FragmentChooseRolesBinding
import id.buildindo.desabangkit.android.ui.adapter.RolesListAdapter
import id.buildindo.desabangkit.android.ui.viewmodel.AuthViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.DummyViewModel

@AndroidEntryPoint
class ChooseRolesFragment : Fragment() {

    private lateinit var _binding: FragmentChooseRolesBinding
    private lateinit var _adapter: RolesListAdapter
    private val _rolesViewModel: DummyViewModel by viewModels()
    private val _authViewModel: AuthViewModel by viewModels()

    private var _dataName = ""
    private var _dataEmail = ""
    private var _dataPassword = ""
    private var _registerData = RegisterData()
    private var _idRoles = ""
    private var _bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseRolesBinding.inflate(layoutInflater, container, false)
        initializeAdapter()
        getRolesData()
        observeData()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _registerData =
            arguments?.getBundleExtra<RegisterData>(Constant.GetIntentType.REGISTER_DATA)!!
        initializeBinding()
    }

    private fun initializeBinding() {
        _binding.apply {
            _binding.btnRegister.setOnClickListener { signUp() }

            _binding.ivBack.setOnClickListener {
                _bundle.sendBundleExtra(
                    Constant.GetIntentType.REGISTER_DATA,
                    RegisterData(
                        name = _dataName,
                        email = _dataEmail,
                        password = _dataPassword
                    )
                )
                Navigation.movePagesFragment(
                    requireParentFragment(),
                    R.id.action_chooseRolesFragment_to_registerFragment,
                    _bundle
                )
            }
        }
    }

    private fun initializeAdapter() {
        _adapter = RolesListAdapter()
        _binding.apply {
            rvRoles.layoutManager = LinearLayoutManager(requireContext())
            rvRoles.adapter = _adapter
            rvRoles.setHasFixedSize(true)
        }
    }

    private fun observeData() {
        _adapter.setRolesList(_rolesViewModel.getRolesData())
        _authViewModel.registerResponse.observe(viewLifecycleOwner) {
            when (it.code) {
                Constant.ResponseCode.SUCCESS -> routeToSuccess(true, it.messages.toString())
                else -> routeToSuccess(false, it.messages.toString())
            }
        }
    }

    private fun routeToSuccess(isSuccess: Boolean, message: String) {
        if (isSuccess) {
            Navigation.movePagesFragment(
                requireParentFragment(),
                R.id.action_chooseRolesFragment_to_registrasionSuccessFragment
            )
        }
        showLoading(false, _binding.progressBar)
        AlertMessage.showSnackbarNoAction(_binding.root, message, 5000)
    }

    private fun getRolesData() = _authViewModel.rolesUser()

    private fun signUp() {
        showLoading(true, _binding.progressBar)
        _authViewModel.rolesResponse.observe(viewLifecycleOwner) { roles ->
            if (roles != null) {
                when (_adapter.rolesName) {
                    Constant.Roles.KONSUMEN -> _idRoles = roles.result?.get(0)?.id ?: ""
                    Constant.Roles.PARTNER -> _idRoles = roles.result?.get(1)?.id ?: ""
                }
            }
        }
        _authViewModel.registerUser(
            RegisterRequest(
                _registerData.name,
                _registerData.email,
                _registerData.password,
                _idRoles
            )
        )
    }
}