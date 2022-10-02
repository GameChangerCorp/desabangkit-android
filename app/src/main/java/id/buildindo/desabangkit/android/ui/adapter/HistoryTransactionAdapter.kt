package id.buildindo.desabangkit.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.buildindo.desabangkit.android.R
import id.buildindo.desabangkit.android.core.data.remote.response.products.ProductHistoryResults
import id.buildindo.desabangkit.android.core.domain.model.Category
import id.buildindo.desabangkit.android.core.utils.StringFormat
import id.buildindo.desabangkit.android.databinding.CardHistoryProductAddedBinding
import id.buildindo.desabangkit.android.databinding.ListCategoryItemBinding
import timber.log.Timber

class HistoryTransactionAdapter : RecyclerView.Adapter<HistoryTransactionAdapter.ViewHolder>() {

    private lateinit var onCategoryClickCallback: OnCategoryClickCallback

    private val historyList = ArrayList<ProductHistoryResults>()

    fun setOnItemClick(onCategoryClickCallback: OnCategoryClickCallback) {
        this.onCategoryClickCallback = onCategoryClickCallback
    }

    fun setCategoryList(transactions: List<ProductHistoryResults>) {
        Timber.d("cek productnya masuk ora $transactions")
        historyList.clear()
        historyList.addAll(transactions)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CardHistoryProductAddedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: ProductHistoryResults) {
//            binding.root.setOnClickListener { onCategoryClickCallback.onItemClicked(history) }

            binding.tvProductName.text = itemView.context.resources.getString(R.string.product_name_placeholder, history.name)
            binding.tvProductCategory.text = itemView.context.resources.getString(R.string.product_category_placeholder, history.category)
            binding.tvProductPrice.text = itemView.context.resources.getString(R.string.currency_placeholder_3, StringFormat.getIndonesianCurrencyFormat(history.price_expected?.toLong() ?: 0))
            binding.tvProductQuantity.text = itemView.context.resources.getString(R.string.product_quantity_placeholder, history.Quantity.toString())
            if (history.is_preorder == true) binding.tvPreorderStatus.visibility = View.VISIBLE else binding.tvPreorderStatus.visibility = View.GONE
            if (history.is_approved == true) {
                binding.tvStatusTransaction.text = itemView.context.resources.getString(R.string.transaction_approve)
                binding.tvStatusTransaction.setTextColor(ContextCompat.getColor(itemView.context, R.color.fern))
            } else {
                binding.tvStatusTransaction.text = itemView.context.resources.getString(R.string.transaction_still_proses)
                binding.tvStatusTransaction.setTextColor(ContextCompat.getColor(itemView.context, R.color.bright_orange))
            }
            binding.tvDateCreated.text = itemView.context.resources.getString(R.string.created_at, StringFormat.formatDateToIndonesian(history.created_at.toString()))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardHistoryProductAddedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = historyList[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = historyList.size

    interface OnCategoryClickCallback {
        fun onItemClicked(history: ProductHistoryResults)
    }
}

