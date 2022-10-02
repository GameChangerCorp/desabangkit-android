package id.buildindo.desabangkit.android.ui.pages.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.domain.model.bundle.register.InputProductData
import id.buildindo.desabangkit.android.core.utils.Constant
import id.buildindo.desabangkit.android.core.utils.DropdownList.createDropdownExposed
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.SendBundleData.getBundleExtra
import id.buildindo.desabangkit.android.core.utils.SendBundleData.sendBundleExtra
import id.buildindo.desabangkit.android.core.utils.ViewVisibility.showLoading
import id.buildindo.desabangkit.android.databinding.FragmentInputProductsDetailsBinding
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel
import timber.log.Timber

@AndroidEntryPoint
class InputProductsDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentInputProductsDetailsBinding
    private val _productsViewModel: ProductsViewModel by viewModels()
    private var _productName: ArrayList<String> = arrayListOf()
    private var _productCategory: ArrayList<String> = arrayListOf()
    private var _productDeliveryOption: ArrayList<String> = arrayListOf(
        "Diantar ke Koperasi", "Diambil oleh Koperasi"
    )
    private val _bundle = Bundle()
    private var _productData = InputProductData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputProductsDetailsBinding.inflate(inflater, container, false)
        initializeLiveData()
        observeLiveData()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _productData =
            arguments?.getBundleExtra<InputProductData>(Constant.GetIntentType.INPUT_PRODUCT_DATA)!!
        Timber.d("product data: $_productData")
        initializeBinding()
    }

    private fun initializeLiveData() {
        _productsViewModel.getAllProduct()
        _productsViewModel.getProductCategories()
        showLoading(true, _binding.progressBar)
    }

    private fun observeLiveData() {
        _productsViewModel.allProduct.observe(viewLifecycleOwner) {
            showLoading(false, _binding.progressBar)
            it.forEach { product ->
                product.name?.let { name ->
                    _productName.add(name)
                }
            }
        }

        _productsViewModel.categories.observe(viewLifecycleOwner) {
            showLoading(false, _binding.progressBar)
            it.forEach { product ->
                product.name?.let { name ->
                    _productCategory.add(name)
                }
            }
        }
    }

    private fun initializeBinding() {
        _binding.apply {
            if (_productData.isPreorder == true){
                edtName.setText(_productData.productName)
                edtName.isEnabled = false
                edtCategory.setText(_productData.productCategory)
                edtCategory.isEnabled = false
            }else{
                createDropdownExposed(
                    requireContext(),
                    R.layout.dropdown_menu,
                    _productName,
                    edtName
                )
                createDropdownExposed(
                    requireContext(),
                    R.layout.dropdown_menu,
                    _productCategory,
                    edtCategory
                )
            }

            createDropdownExposed(
                requireContext(),
                R.layout.dropdown_menu,
                _productDeliveryOption,
                edtDeliveryOption
            )

            btnNext.setOnClickListener {
                val productName = edtName.text.toString()
                val productCategory = edtCategory.text.toString()
                val productQuantity = edtQuantity.text.toString()
                val productPrice = edtPriceExpected.text.toString()
                val productDeliveryOption = edtDeliveryOption.text.toString()

                if (_productData.isPreorder == true){
                    _bundle.sendBundleExtra(
                        Constant.GetIntentType.INPUT_PRODUCT_DATA, InputProductData(
                            productName = productName,
                            productCategory = productCategory,
                            productQuantity = productQuantity,
                            productPrice = productPrice,
                            productDeliveryOption = productDeliveryOption,
                            productPhoto = _productData.productPhoto,
                            isPreorder = _productData.isPreorder
                        )
                    )
                }else{
                    _bundle.sendBundleExtra(
                        Constant.GetIntentType.INPUT_PRODUCT_DATA, InputProductData(
                            productName = productName,
                            productCategory = productCategory,
                            productQuantity = productQuantity,
                            productPrice = productPrice,
                            productDeliveryOption = productDeliveryOption,
                            productPhoto = _productData.productPhoto
                        )
                    )
                }

                Navigation.movePagesFragment(
                    requireParentFragment(),
                    R.id.action_inputProductsDetailsFragment_to_inputProductDeliveryOptionFragment,
                    _bundle
                )
            }
        }
    }
}