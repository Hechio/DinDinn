package com.stevehechio.apps.dindinnassigment.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stevehechio.apps.dindinnassigment.R
import com.stevehechio.apps.dindinnassigment.databinding.ItemOrderListBinding
import com.stevehechio.apps.dindinnassigment.di.component.DaggerApiComponent
import com.stevehechio.apps.dindinnassigment.repository.data.model.Order
import javax.inject.Inject

/**
 * Created by stevehechio on 6/27/21
 */
class OrderAdapter(values: List<Order>?) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var values: List<Order>? = null
    private var mContext: Context? = null

    private val viewPool = RecyclerView.RecycledViewPool()

    fun setOrder(values: List<Order>?,mContext: Context?){
        this.values = values
        this.mContext = mContext
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ItemOrderListBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        values?.get(position)?.let { holder.bindViews(order = it) }

    }

    override fun getItemCount(): Int {
        return values?.size ?: 0
    }

    inner class OrderViewHolder(private val binding: ItemOrderListBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bindViews(order: Order){
            if (binding.llProgress.childCount > 0){
                binding.llProgress.removeAllViews()
            }
            val orderAddonAdapter = OrderAddonAdapter(order.addon)
            val innerLLM = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            innerLLM.initialPrefetchItemCount = 2 // depends on screen size
            binding.rvOrderItems.apply {
                setHasFixedSize(true)
                layoutManager = innerLLM
                setRecycledViewPool(viewPool)
            }

            val childLayoutManager = LinearLayoutManager(binding.rvOrderItems.context, LinearLayoutManager.VERTICAL, false)
            binding.rvOrderItems.apply {
                layoutManager = childLayoutManager
                adapter = orderAddonAdapter
                setRecycledViewPool(viewPool)
            }
            binding.tvOrderId.text = "#${order.id}"
            orderAddonAdapter.setOrderAddon(order.addon)
            binding.tvCounts.text = "${orderAddonAdapter.itemCount} items"
            setProgressView(5,3,binding.llProgress)
        }
        private fun setProgressView(totalCounts: Int, remCounts: Int, linearLayout: LinearLayout) {

            for (i in 0 until remCounts) {
                val progressView = View(mContext)
                val params: LinearLayoutCompat.LayoutParams = LinearLayoutCompat.LayoutParams(40, 10)
                params.setMargins(0, 0, 2, 0)
                progressView.layoutParams = params
                progressView.background = mContext?.let {
                    ResourcesCompat.getDrawable(
                        it.resources,
                        R.drawable.progress_grey_background,
                        it.theme
                    )
                }
                linearLayout.addView(progressView)
            }
            val remProgress = totalCounts - remCounts

            for (i in 0 until remProgress) {
                val progressView = View(mContext)
                val params: LinearLayoutCompat.LayoutParams = LinearLayoutCompat.LayoutParams(40, 10)
                params.setMargins(0, 0, 2, 0)
                progressView.layoutParams = params
                progressView.background = mContext?.let {
                    ResourcesCompat.getDrawable(
                        it.resources,
                        R.drawable.progress_orange_background,
                        it.theme
                    )
                }

                linearLayout.addView(progressView)
            }
        }
    }


}