package id.buildindo.desabangkit.android.ui.pages.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.databinding.FragmentSuccessInputProductBinding

@AndroidEntryPoint
class SuccessInputProductFragment : Fragment() {

    private lateinit var _binding: FragmentSuccessInputProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessInputProductBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeBinding()
    }

    private fun initializeBinding() {
        _binding.apply {
            btnGoToHome.setOnClickListener {
                Navigation.movePagesFragment(
                    requireParentFragment(),
                    R.id.action_successInputProductFragment_to_navigation_beranda
                )
            }

            btnGoToInputProduct.setOnClickListener {
                Navigation.movePagesFragment(
                    requireParentFragment(),
                    R.id.action_successInputProductFragment_to_inputProductsPhotoFragment
                )
            }
        }
    }

}