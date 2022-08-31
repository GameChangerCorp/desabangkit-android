package id.buildindo.desabangkit.android.ui.pages.partner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.databinding.FragmentSupplyProductBinding

@AndroidEntryPoint
class SupplyProductFragment : Fragment() {

    private lateinit var _binding : FragmentSupplyProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupplyProductBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeBinding()
    }

    private fun initializeBinding() {
        _binding.apply {
            btnInputProduct.setOnClickListener {
                val bundle = bundleOf(Constant.BundleName.OPEN_PAGES_FROM to Constant.Pages.SUPPLY_PRODUCT_PAGES)
                Navigation.movePagesFragment(requireParentFragment(), R.id.action_navigation_supply_to_inputProductsFragment2, bundle)
            }
        }
    }

}