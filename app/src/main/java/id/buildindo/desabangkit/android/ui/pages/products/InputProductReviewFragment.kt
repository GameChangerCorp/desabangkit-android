package id.buildindo.desabangkit.android.ui.pages.products

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.data.remote.response.products.PostProductRequest
import id.buildindo.desabangkit.android.core.domain.model.bundle.register.InputProductData
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.SendBundleData.getBundleExtra
import id.buildindo.desabangkit.android.core.utils.StringFormat
import id.buildindo.desabangkit.android.core.utils.ViewVisibility
import id.buildindo.desabangkit.android.databinding.FragmentInputProductReviewBinding
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel
import timber.log.Timber

@AndroidEntryPoint
class InputProductReviewFragment : Fragment() {

    private lateinit var _binding: FragmentInputProductReviewBinding
    private var _productData = InputProductData()
    private val _productViewModel: ProductsViewModel by viewModels()
    private lateinit var datastoreViewModel: DatastoreViewModel

    private var _userId = ""
    private var _address = ""
    private var _cooperationName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputProductReviewBinding.inflate(inflater, container, false)
        datastoreViewModel = ViewModelProvider(this)[DatastoreViewModel::class.java]
        observeDataStore()
        observeLiveData()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _productData =
            arguments?.getBundleExtra<InputProductData>(Constant.GetIntentType.INPUT_PRODUCT_DATA)!!
        Timber.d("cek value ${_productData.address}")
        initializeBinding()
    }

    private fun initializeBinding() {
        _binding.apply {
            productName.text = _productData.productName
            productCategory.text = _productData.productCategory
            productPrice.text = resources.getString(
                R.string.currency_placeholder,
                StringFormat.getIndonesianCurrencyFormat(_productData.productPrice.toLong())
            )
            productUnit.text = _productData.productQuantity
            productDeliveryOption.text = _productData.productDeliveryOption

            _address = _productData.address
            _cooperationName = _productData.cooperationName

            if (_productData.isPreorder == true){
                productStatus.text = resources.getString(R.string.preorder_sticker)
            }else{
                productStatus.text = resources.getString(R.string.normal_transaction)
            }

            if (_productData.productDeliveryOption.contains("Diantar")) {
                titleProductCooperationName.text = resources.getString(R.string.cooperation_name)
                productCooperationName.text = _cooperationName
            } else {
                titleProductCooperationName.text = resources.getString(R.string.address)
                productCooperationName.text = _address
            }
            Glide.with(this@InputProductReviewFragment)
                .load(_productData.productPhoto)
                .into(ivProductsPhoto)


            val total = _productData.productPrice.toLong() * _productData.productQuantity.toLong()
            productTotal.text = resources.getString(
                R.string.currency_placeholder_2,
                StringFormat.getIndonesianCurrencyFormat(total)
            )

            btnSubmit.setOnClickListener {
                ViewVisibility.showLoading(true, _binding.progressBar)
                _productViewModel.postProduct(
                    preorder = _productData.isPreorder,
                    PostProductRequest(
                        photo_url = _productData.productPhoto,
                        name = _productData.productName,
                        category = _productData.productCategory,
                        quantity = _productData.productQuantity.toInt(),
                        price_expected = _productData.productPrice.toInt(),
                        deliver_option = _productData.productDeliveryOption,
                        useraddress = _productData.address,
                        userid = _userId,
                        cooperationId = "6309dd650b3a4f92a4c9f3c9"
                    )
                )
            }
        }
    }

    private fun observeLiveData() {
        _productViewModel.postProduct.observe(viewLifecycleOwner) {
            if (it != null) {
                ViewVisibility.showLoading(false, _binding.progressBar)
                Navigation.movePagesFragment(
                    requireParentFragment(),
                    R.id.action_inputProductReviewFragment_to_successInputProductFragment
                )
            }
        }
    }

    private fun observeDataStore() {
        datastoreViewModel.getUserId().observe(viewLifecycleOwner) {
            _userId = it
        }
    }

}