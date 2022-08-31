package id.buildindo.desabangkit.android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.buildindo.desabangkit.android.core.data.remote.response.products.products.Product
import id.buildindo.desabangkit.android.core.utils.StringFormat
import id.buildindo.desabangkit.android.databinding.CardProductItemBinding
import timber.log.Timber

class GridProductAdapter : RecyclerView.Adapter<GridProductAdapter.ViewHolder>() {

    private lateinit var onProductClick: OnProductClick

    private val productList = ArrayList<Product>()

    fun onProductClick(onProductClick: OnProductClick) {
        this.onProductClick = onProductClick
    }
    fun setProductList(roles: List<Product>) {
        Timber.d("cek productnya masuk ora $roles")
        productList.clear()
        productList.addAll(roles)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CardProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.root.setOnClickListener { onProductClick.onItemClicked(product) }
            binding.tvProductName.text = product.name
            binding.tvProductCategory.text = product.category
            binding.tvProductPrice.text = StringFormat.currencyFormat(product.price, product.unit)
            Glide.with(itemView.context)
                .asBitmap()
                .load(product.photoUrl)
                .into(binding.productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size
}

interface OnProductClick {
    fun onItemClicked(product: Product)
}