package id.buildindo.desabangkit.android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.buildindo.desabangkit.android.core.domain.model.Category
import id.buildindo.desabangkit.android.databinding.ListCategoryItemBinding
import timber.log.Timber

class VerticalCategoriesAdapter : RecyclerView.Adapter<VerticalCategoriesAdapter.ViewHolder>() {

    private lateinit var onCategoryClickCallback: OnCategoryClickCallback

    private val productList = ArrayList<Category>()

    fun setOnItemClick(onCategoryClickCallback: OnCategoryClickCallback) {
        this.onCategoryClickCallback = onCategoryClickCallback
    }

    fun setCategoryList(category: List<Category>) {
        Timber.d("cek productnya masuk ora $category")
        productList.clear()
        productList.addAll(category)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.root.setOnClickListener { onCategoryClickCallback.onItemClicked(category) }

            binding.tvProductName.text = category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = productList[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = productList.size

    interface OnCategoryClickCallback {
        fun onItemClicked(category: Category)
    }
}

