package id.buildindo.desabangkit.android.ui.pages.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.domain.model.bundle.register.InputProductData
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.SendBundleData.getBundleExtra
import id.buildindo.desabangkit.android.core.utils.SendBundleData.sendBundleExtra
import id.buildindo.desabangkit.android.core.utils.ViewVisibility
import id.buildindo.desabangkit.android.databinding.FragmentInputProductDeliveryOptionBinding
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel
import timber.log.Timber

@AndroidEntryPoint
class InputProductDeliveryOptionFragment : Fragment() {

    private lateinit var _binding: FragmentInputProductDeliveryOptionBinding
    private val _productsViewModel: ProductsViewModel by viewModels()
    private var _productData = InputProductData()
    private var _dataDeliveryOption = ""
    private var _cooperationName = ""
    private var _cooperationId = ""
    private val _bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputProductDeliveryOptionBinding.inflate(inflater, container, false)
        initializeLiveData()
        observeLiveData()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _productData =
            arguments?.getBundleExtra<InputProductData>(Constant.GetIntentType.INPUT_PRODUCT_DATA)!!
        _dataDeliveryOption = _productData.productDeliveryOption

        Timber.d("Product Data: ${_productData.productDeliveryOption}")
        initializeBinding()
    }

    private fun initializeBinding() {
        _binding.apply {
            if (_dataDeliveryOption == "Diantar ke Koperasi") {
                clCooperationCard.visibility = View.VISIBLE
                clAddressCard.visibility = View.GONE
                tvTitle.text = resources.getString(R.string.text_title_delivery_option)
            } else {
                clAddressCard.visibility = View.VISIBLE
                clCooperationCard.visibility = View.GONE
                tvTitle.text = resources.getString(R.string.text_title_delivery_option_2)
            }

            btnGotoReviewPage.setOnClickListener {
                getEditTextString()
                if (_dataDeliveryOption == "Diantar ke Koperasi") {
                    _bundle.sendBundleExtra(
                        Constant.GetIntentType.INPUT_PRODUCT_DATA,
                        InputProductData(
                            productPhoto = _productData.productPhoto,
                            productName = _productData.productName,
                            productCategory = _productData.productCategory,
                            productPrice = _productData.productPrice,
                            productQuantity = _productData.productQuantity,
                            productDeliveryOption = _dataDeliveryOption,
                            cooperationName = _cooperationName,
                            cooperationId = _cooperationId,
                            isPreorder = _productData.isPreorder,
                        )
                    )
                } else {
                    _bundle.sendBundleExtra(
                        Constant.GetIntentType.INPUT_PRODUCT_DATA,
                        InputProductData(
                            productPhoto = _productData.productPhoto,
                            productName = _productData.productName,
                            productCategory = _productData.productCategory,
                            productPrice = _productData.productPrice,
                            productQuantity = _productData.productQuantity,
                            productDeliveryOption = _dataDeliveryOption,
                            address = getEditTextString(),
                            isPreorder = _productData.isPreorder
                        )
                    )
                }

                Navigation.movePagesFragment(
                    requireParentFragment(),
                    R.id.action_inputProductDeliveryOptionFragment_to_inputProductReviewFragment,
                    _bundle
                )
            }
        }
    }

    private fun initializeLiveData() {
        _productsViewModel.cooperationData("da654461-ff49-4504-8c28-002b9e63176f")
        ViewVisibility.showLoading(true, _binding.progressBar)
    }

    private fun observeLiveData() {
        _productsViewModel.cooperation.observe(viewLifecycleOwner) {
            if (it != null) {
                ViewVisibility.showLoading(false, _binding.progressBar)
                _binding.apply {
                    if (it.data != null) {
                        it.data.apply {
                            tvCooperation.text = name
                            _cooperationName = name.toString()
                            _cooperationId = id.toString()
                            tvStreet.text = resources.getString(
                                R.string.address_placeholder,
                                address?.street,
                                address?.village,
                                address?.district,
                                address?.city,
                                address?.province
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getEditTextString(): String {
        val address = _binding.edtAddress.text.toString()
        val village = _binding.edtVillage.text.toString()
        val district = _binding.edtDistrict.text.toString()
        val city = _binding.edtCity.text.toString()
        val province = _binding.edtProvince.text.toString()

        return resources.getString(
            R.string.address_placeholder,
            address,
            village,
            district,
            city,
            province
        )
    }
}