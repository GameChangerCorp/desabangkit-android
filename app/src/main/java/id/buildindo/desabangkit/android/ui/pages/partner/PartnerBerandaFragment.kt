package id.buildindo.desabangkit.android.ui.pages.partner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.domain.model.Products
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.core.utils.ViewVisibility
import id.buildindo.desabangkit.android.databinding.FragmentPartnerBerandaBinding
import id.buildindo.desabangkit.android.ui.adapter.HorizontalProductAdapter
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel

@AndroidEntryPoint
class PartnerBerandaFragment : Fragment() {

    private lateinit var _dataStoreViewModel: DatastoreViewModel
    private val _productsViewModel: ProductsViewModel by viewModels()
    private lateinit var _adapter: HorizontalProductAdapter
    private lateinit var _binding: FragmentPartnerBerandaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartnerBerandaBinding.inflate(inflater, container, false)
        initializeViewModel()
        observeDataStore()
        observeData()
        initializeAdapter()
        return _binding.root
    }

    private fun observeDataStore() {
        _dataStoreViewModel.getUsername().observe(viewLifecycleOwner) { name ->
            _binding.usernameText.text = name
        }
    }

    private fun initializeViewModel() {
        _dataStoreViewModel = ViewModelProvider(requireActivity())[DatastoreViewModel::class.java]
    }

    private fun initializeAdapter() {
        _adapter = HorizontalProductAdapter()
        _binding.apply {
            rvProduct.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvProduct.adapter = _adapter
        }

        _adapter.onProductClick(object : HorizontalProductAdapter.OnProductClick {
            override fun onItemClicked(product: Products) {
                val bundle = Bundle()
                bundle.putString("productName", product.name)
                bundle.putString("productPrice", product.price)
                bundle.putString("productCategory", product.category)
                bundle.putString("productUnit", product.unit)
                bundle.putString("productPhoto", product.photo)
                Navigation.movePagesFragment(
                    requireParentFragment(),
                    R.id.action_navigation_beranda_to_detailProductFragment2,
                    bundle
                )
            }
        })
    }

    private fun observeData() {
        ViewVisibility.showLoading(true, _binding.progressBar)
        _productsViewModel.getAllProduct()

        _productsViewModel.allProduct.observe(viewLifecycleOwner) {
            it?.let { products ->
                ViewVisibility.showLoading(false, _binding.progressBar)
                _adapter.setProductList(products)
            }
        }
    }
}