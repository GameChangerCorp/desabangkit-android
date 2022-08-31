package id.buildindo.desabangkit.android.ui.pages.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.Product
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.databinding.FragmentCustomerBerandaBinding
import id.buildindo.desabangkit.android.ui.adapter.HorizontalProductAdapter
import id.buildindo.desabangkit.android.ui.adapter.OnProductClick
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel
import timber.log.Timber

@AndroidEntryPoint
class CustomerBerandaFragment : Fragment() {

    private val _productsViewModel : ProductsViewModel by viewModels()
    private lateinit var _dataStoreViewModel : DatastoreViewModel
    private lateinit var _adapter: HorizontalProductAdapter
    private lateinit var _binding: FragmentCustomerBerandaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerBerandaBinding.inflate(inflater, container, false)
        initializeViewModel()
        observeDataStore()
        observeData()
        initializeAdapter()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeBinding()
    }

    private fun initializeBinding() {
        _binding.apply {
            showAll1Text.setOnClickListener {
                Navigation.movePagesFragment(requireParentFragment(), R.id.action_customerBerandaFragment_to_categoriesFragment)
            }
        }
    }

    private fun initializeAdapter() {
        _adapter = HorizontalProductAdapter()
        _binding.apply {
            rvProduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvProduct.adapter = _adapter
        }

        _adapter.onProductClick(object : OnProductClick{
            override fun onItemClicked(product: Product) {
                val bundle = Bundle()
                bundle.putString("productName", product.name)
                bundle.putString("productPrice", product.price)
                bundle.putString("productCategory", product.category)
                bundle.putString("productUnit", product.unit)
                bundle.putString("productPhoto", product.photoUrl)
                Navigation.movePagesFragment(requireParentFragment(), R.id.action_navigation_beranda_to_detailProductFragment, bundle)
            }
        })
    }

    private fun observeData() {
        _productsViewModel.getAllProduct()
        _productsViewModel.allProduct.observe(viewLifecycleOwner) {
            if (it != null) {
                it.data?.products?.let { products -> _adapter.setProductList(products) }
            }
        }
    }

    private fun observeDataStore() {
        _dataStoreViewModel.getUsername().observe(viewLifecycleOwner) { name ->
            _binding.usernameText.text = name
        }
    }

    private fun initializeViewModel() {
        _dataStoreViewModel = ViewModelProvider(requireActivity())[DatastoreViewModel::class.java]
    }
}