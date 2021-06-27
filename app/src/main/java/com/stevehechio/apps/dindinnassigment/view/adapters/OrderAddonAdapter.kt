package com.stevehechio.apps.dindinnassigment.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stevehechio.apps.dindinnassigment.databinding.ItemOrderItemListBinding
import com.stevehechio.apps.dindinnassigment.repository.data.model.Addon


/**
 * Created by stevehechio on 6/27/21
 */
class OrderAddonAdapter(addon: List<Addon>): RecyclerView.Adapter<OrderAddonAdapter.OrderAddonViewHolder>() {
    private var valuesAddons: List<Addon>? = null


    fun setOrderAddon(valuesAddons: List<Addon>?){
        this.valuesAddons = valuesAddons
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAddonViewHolder {
        return OrderAddonViewHolder(ItemOrderItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: OrderAddonViewHolder, position: Int) {
        valuesAddons?.get(position)?.let { holder.bindViews(it) }
    }

    override fun getItemCount(): Int {
        return valuesAddons?.size ?: 0
    }

    inner class OrderAddonViewHolder(private val binding: ItemOrderItemListBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bindViews(addon: Addon){
            binding.tvName.text = "${addon.quantity} ${addon.title}"
            binding.tvProtein.text = "# Protein (${addon.quantity})"
        }
    }
}