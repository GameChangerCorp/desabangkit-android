package id.buildindo.desabangkit.android.ui.pages.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.Navigation
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
            tvProductPrice.text = StringFormat.currencyFormat(_price, _unit)
            Glide.with(this@DetailProductFragment)
                .asBitmap()
                .load(_imageUrl)
                .into(_binding.imageDetail)
            btnPreOrder.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("productName", _name)
                bundle.putString("productPrice", _price)
                bundle.putString("productCategory", _categories)
                bundle.putString(Constant.BundleName.OPEN_PAGES_FROM, Constant.Pages.DETAIL_PRODUCT_PAGES)
                Navigation.movePagesFragment(requireParentFragment(), R.id.action_detailProductFragment_to_inputProductsFragment, bundle)
            }
        }
    }
}