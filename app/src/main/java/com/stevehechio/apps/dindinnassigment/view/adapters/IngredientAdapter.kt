package com.stevehechio.apps.dindinnassigment.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stevehechio.apps.dindinnassigment.R
import com.stevehechio.apps.dindinnassigment.databinding.ItemIngredientListBinding
import com.stevehechio.apps.dindinnassigment.repository.data.model.Ingredient

/**
 * Created by stevehechio on 6/28/21
 */
class IngredientAdapter(values: List<Ingredient>?):
    ListAdapter<Ingredient,IngredientAdapter.IngredientViewHolder>(IngredientDiffUtil()) {
    private var values: List<Ingredient>? = null
    private var mContext: Context? = null

    fun setContext(mContext: Context?){
        this.mContext = mContext
    }

    inner class IngredientViewHolder(private val binding: ItemIngredientListBinding): RecyclerView.ViewHolder(binding.root){
        fun bindViews(ingredient: Ingredient){
            binding.tvName.text = ingredient.name
            val qty = ingredient.available
            binding.tvQty.text = "$qty"
            if (qty > 9){
                binding.tvAvailable.background = mContext?.let { ResourcesCompat.getDrawable(
                    it.resources, R.drawable.available_orange,it.theme
                ) }


                binding.llAvailable.background = mContext?.let { ResourcesCompat.getDrawable(
                    it.resources, R.drawable.border_orange_background,it.theme
                ) }
            }else {
                binding.tvAvailable.background = mContext?.let { ResourcesCompat.getDrawable(
                    it.resources, R.drawable.available_grey,it.theme
                ) }

                binding.llAvailable.background = mContext?.let { ResourcesCompat.getDrawable(
                    it.resources, R.drawable.border_grey_background,it.theme
                ) }
            }
            val url = ingredient.url
            mContext?.let {
                Glide.with(it)
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.ic_baseline_fastfood_24)
                    .into(binding.ivImage)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(ItemIngredientListBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        getItem(position).let { holder.bindViews(it) }
    }

}
class IngredientDiffUtil : DiffUtil.ItemCallback<Ingredient>(){
    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return newItem == oldItem
    }

}
