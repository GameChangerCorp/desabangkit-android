package id.buildindo.desabangkit.android.ui.pages.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.core.utils.Navigation.finishActivity
import id.buildindo.desabangkit.android.databinding.FragmentRegistrasionSuccessBinding

@AndroidEntryPoint
class RegistrasionSuccessFragment : Fragment() {

    private lateinit var _binding: FragmentRegistrasionSuccessBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finishActivity(activity!!)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrasionSuccessBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.btnGoToLogin.setOnClickListener {
            activity?.let { isFinished ->
                finishActivity(
                    isFinished
                )
            }
        }
    }
}