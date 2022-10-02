package id.buildindo.desabangkit.android.ui.pages.partner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.databinding.FragmentPartnerTransactionBinding
import id.buildindo.desabangkit.android.ui.adapter.HistoryTransactionAdapter
import id.buildindo.desabangkit.android.ui.viewmodel.DatastoreViewModel
import id.buildindo.desabangkit.android.ui.viewmodel.ProductsViewModel
import timber.log.Timber
@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private lateinit var _binding: FragmentPartnerTransactionBinding
    private lateinit var _adapter: HistoryTransactionAdapter
    private val _productViewModel: ProductsViewModel by viewModels()
    private lateinit var _viewModelDataStore: DatastoreViewModel

    private var _userId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartnerTransactionBinding.inflate(inflater, container, false)
        initializeViewModel()
        initializeAdapter()
        observableDataStore()
        initializeMenu()
        observableLiveData()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this)[DatastoreViewModel::class.java]
    }

    private fun observableDataStore(){
        _viewModelDataStore.getUserId().observe(viewLifecycleOwner) {
            _userId = it
            Timber.d("cek $it")
            showLoading(true)
            _productViewModel.getProductHistory(_userId)
        }
    }

    private fun initializeAdapter() {
        _adapter = HistoryTransactionAdapter()
        _binding.rvTransaction.adapter = _adapter
        _binding.rvTransaction.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun observableLiveData() {
        _productViewModel.transaction.observe(viewLifecycleOwner) {
        }
        _productViewModel.productHistory.observe(viewLifecycleOwner) {
            if (it != null){
                showLoading(false)
                _adapter.setCategoryList(it.result!!)
            }
        }
        _productViewModel.productHistoryWithPreorder.observe(viewLifecycleOwner) {
            if (it != null){
                showLoading(false)
                _adapter.setCategoryList(it.result!!)
            }
        }
    }

    private fun initializeMenu() {
        val popUpMenu = PopupMenu(requireContext(), _binding.ivMenu)
        popUpMenu.menuInflater.inflate(R.menu.history_sort_menu, popUpMenu.menu)
        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.getAllHistory -> {
                    showLoading(true)
                    _productViewModel.getProductHistory(_userId)
                    true
                }
                R.id.preorderTrue -> {
                    showLoading(true)
                    _productViewModel.getProductHistoryWithPreorderStatus(_userId, true)
                    true
                }
                R.id.preorderFalse -> {
                    showLoading(true)
                    _productViewModel.getProductHistoryWithPreorderStatus(_userId, false)
                    true
                }
                else -> false
            }
        }

        _binding.ivMenu.setOnClickListener {
            popUpMenu.show()
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            _binding.rvTransaction.visibility = View.GONE
            _binding.progressBar.visibility = View.VISIBLE
            _binding.progressBar.startShimmer()
        } else {
            _binding.rvTransaction.visibility = View.VISIBLE
            _binding.progressBar.visibility = View.GONE
            _binding.progressBar.stopShimmer()
        }
    }
}