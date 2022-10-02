package id.buildindo.desabangkit.android.ui.pages.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.domain.model.Products
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.databinding.FragmentProductBinding
import id.buildindo.desabangkit.android.ui.adapter.GridProductAdapter
import id.buildindo.desabangkit.android.ui.adapter.OnProductClick
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel
import timber.log.Timber

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private lateinit var _binding : FragmentProductBinding
    private lateinit var _adapter : GridProductAdapter
    private val _productsViewModel : ProductsViewModel by viewModels()
    private var _id = ""
    private var _name = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        initializeAdapter()
        observeData()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initializeAdapter() {
        _adapter = GridProductAdapter()
        _binding.apply {
            rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
            rvProduct.adapter = _adapter
        }
        _adapter.onProductClick(object : OnProductClick{
            override fun onItemClicked(product: Products) {
                val bundle = Bundle()
                bundle.putString("productName", product.name)
                bundle.putString("productPrice", product.price)
                bundle.putString("productCategory", product.category)
                bundle.putString("productUnit", product.unit)
                bundle.putString("productPhoto", product.photo)
                Navigation.movePagesFragment(requireParentFragment(), R.id.action_productFragment_to_detailProductFragment, bundle)
            }
        })
    }

    private fun observeData() {
        _id = arguments?.getString("categories").toString()
        _name = arguments?.getString("name").toString()

        _binding.categoryTitle.text = _name
        Timber.d("cobaa cek dapet ga ini ada id nya $_id")
        _productsViewModel.getProductById(_id)
        _productsViewModel.productById.observe(viewLifecycleOwner) { products ->
           _adapter.setProductList(products)
        }
    }
}