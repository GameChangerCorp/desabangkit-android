package id.buildindo.desabangkit.android.ui.pages.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.domain.model.bundle.register.InputProductData
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.SendBundleData.sendBundleExtra
import id.buildindo.desabangkit.android.core.utils.StringFormat
import id.buildindo.desabangkit.android.databinding.FragmentDetailProductBinding

@AndroidEntryPoint
class DetailProductFragment : Fragment() {

    private lateinit var _binding : FragmentDetailProductBinding
    private var _name = ""
    private var _categories = ""
    private var _price = ""
    private var _unit = ""
    private var _imageUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        initializeView()
    }


    private fun getBundleData() {
        _name = arguments?.getString("productName").toString()
        _categories = arguments?.getString("productCategory").toString()
        _price = arguments?.getString("productPrice").toString()
        _unit = arguments?.getString("productUnit").toString()
        _imageUrl = arguments?.getString("productPhoto").toString()
    }

    private fun initializeView() {
        _binding.apply {
            tvProductName.text = _name
            tvProductCategory.text = _categories
            tvProductPrice.text = resources.getString(R.string.currency_placeholder_3, StringFormat.currencyFormat(_price))
            Glide.with(this@DetailProductFragment)
                .asBitmap()
                .load(_imageUrl)
                .into(_binding.imageDetail)
            btnPreOrder.setOnClickListener {
                val bundle = Bundle()
                bundle.sendBundleExtra(
                    Constant.GetIntentType.INPUT_PRODUCT_DATA,
                    InputProductData(
                        productName = _name,
                        productCategory = _categories,
                        isPreorder = true
                    )
                )
                Navigation.movePagesFragment(requireParentFragment(), R.id.action_detailProductFragment2_to_inputProductsPhotoFragment, bundle)
            }
        }
    }
}