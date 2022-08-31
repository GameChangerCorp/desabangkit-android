package id.buildindo.desabangkit.android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.buildindo.desabangkit.android.core.domain.model.Roles
import id.buildindo.desabangkit.android.databinding.RolesItemBinding
import timber.log.Timber

class RolesListAdapter : RecyclerView.Adapter<RolesListAdapter.ViewHolder>() {

    private val rolesList = ArrayList<Roles>()
    var selectedPosition = -1
    var rolesName = ""
    fun setRolesList(roles: List<Roles>) {
        rolesList.clear()
        rolesList.addAll(roles)
    }

    inner class ViewHolder(val binding: RolesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        internal fun bind(roles: Roles) {
            binding.tvRoles.text = roles.name
            binding.tvDesc.text = roles.desc
            binding.ivRoles.setImageResource(roles.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RolesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val roles = rolesList[position]
        holder.binding.rbRoles.isChecked = position == selectedPosition
        holder.binding.rbRoles.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                val copySelectedPosition = selectedPosition
                selectedPosition = holder.bindingAdapterPosition
                notifyItemChanged(copySelectedPosition)
                notifyItemChanged(selectedPosition)

                rolesName = holder.binding.tvRoles.text.toString()
                Timber.d("cek dapet role name nya ga yak ges $rolesName")
                Timber.d("cek value radio button $selectedPosition sama posisi dari ${holder.bindingAdapterPosition}")
            }
        }

        holder.bind(roles)
    }

    override fun getItemCount(): Int = rolesList.size
}
