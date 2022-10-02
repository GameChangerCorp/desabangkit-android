package id.buildindo.desabangkit.android.ui.pages.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.domain.model.Category
import id.buildindo.desabangkit.android.core.utils.Navigation
import id.buildindo.desabangkit.android.databinding.FragmentCategoriesBinding
import id.buildindo.desabangkit.android.ui.adapter.VerticalCategoriesAdapter
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private val _productsViewModel : ProductsViewModel by viewModels()
    private lateinit var _adapter : VerticalCategoriesAdapter
    private lateinit var _binding : FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        initializeAdapter()
        observeData()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initializeAdapter() {
        _adapter = VerticalCategoriesAdapter()
        _binding.apply {
            rvCategory.layoutManager = LinearLayoutManager(requireContext())
            rvCategory.adapter = _adapter
        }

        _adapter.setOnItemClick(object : VerticalCategoriesAdapter.OnCategoryClickCallback {
            override fun onItemClicked(category: Category) {
                val bundle = Bundle()
                bundle.putString("categories", category.id)
                bundle.putString("name", category.name)
                Navigation.movePagesFragment(requireParentFragment(), R.id.action_categoriesFragment_to_productFragment, bundle)
            }
        })
    }

    private fun observeData() {
        _productsViewModel.getProductCategories()
        _productsViewModel.categories.observe(viewLifecycleOwner) {
            _adapter.setCategoryList(it)
        }
    }
}