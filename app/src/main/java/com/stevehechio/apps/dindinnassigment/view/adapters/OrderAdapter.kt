package com.stevehechio.apps.dindinnassigment.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stevehechio.apps.dindinnassigment.R
import com.stevehechio.apps.dindinnassigment.databinding.ItemOrderListBinding
import com.stevehechio.apps.dindinnassigment.repository.data.model.Order
import com.stevehechio.apps.dindinnassigment.utils.DateUtils
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Created by stevehechio on 6/27/21
 */
class OrderAdapter(values: List<Order>?) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var values: List<Order>? = null
    private var mContext: Context? = null
    var onAcceptExpireButtonClickListener: OnAcceptExpireButtonClickListener? = null



    private val viewPool = RecyclerView.RecycledViewPool()

    fun setOrder(values: List<Order>?,mContext: Context?){
        this.values = values
        this.mContext = mContext
        notifyDataSetChanged()
    }fun setOrderList(values: List<Order>?){
        this.values = values
        notifyDataSetChanged()
    }

    fun getOrderList():ArrayList<Order> {
        val orderList = ArrayList<Order>()
        values?.forEach { orderList.add(it) }
        return orderList
    }
    fun setOnAcceptExpireButtonListener(onAcceptExpireButtonClickListener: OnAcceptExpireButtonClickListener){
        this.onAcceptExpireButtonClickListener = onAcceptExpireButtonClickListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ItemOrderListBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        values?.get(position)?.let { holder.bindViews(order = it) }

    }

    override fun getItemCount(): Int {
        return values?.size ?: 0
    }

    inner class OrderViewHolder(private val binding: ItemOrderListBinding): RecyclerView.ViewHolder(binding.root){

        val timerHandler = Handler(Looper.getMainLooper())
        var intervalTimeHandler: Long = 1000

        //maxHandlerTime is  alerted time - created time
        var maxHandlerTime: Long = 0

        // timePassed = now - created at
        var timePassed: Long = 0



        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun bindViews(order: Order) {

            val alertedAt = DateUtils.formatDateStr(order.alerted_at)
            val now = Date().time
            val createdAt = DateUtils.formatDateStr(order.created_at)


            maxHandlerTime = alertedAt - createdAt

            timePassed = now - createdAt

           invalidateViews()
            val orderAddonAdapter = OrderAddonAdapter(order.addon)
            val innerLLM = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            innerLLM.initialPrefetchItemCount = 2 // depends on screen size
            binding.rvOrderItems.apply {
                setHasFixedSize(true)
                layoutManager = innerLLM
                setRecycledViewPool(viewPool)
            }

            val childLayoutManager = LinearLayoutManager(
                binding.rvOrderItems.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.rvOrderItems.apply {
                layoutManager = childLayoutManager
                adapter = orderAddonAdapter
                setRecycledViewPool(viewPool)
            }
            binding.tvOrderId.text = "#${order.id}"
            orderAddonAdapter.setOrderAddon(order.addon)
            binding.tvCounts.text = "${order.addon.size} items"
            binding.tvTimeAlert.text = "at ${DateUtils.formatGMTDateStr(order.alerted_at)}".lowercase()
            setProgressView(5, 0, binding.llProgress)
            timerHandler.postDelayed(timerRunnable, intervalTimeHandler)

            binding.btnAccept.setOnClickListener {
                onAcceptExpireButtonClickListener?.onAcceptExpireClicked(order,
                    timePassed > maxHandlerTime) }
            if (timePassed > maxHandlerTime){
                binding.btnAccept.background = mContext?.let {
                    ResourcesCompat.getDrawable(
                        it.resources,
                        R.drawable.button_expired_background, it.theme
                    )
                }
                binding.btnAccept.text = mContext?.getString(R.string.expired)
            }else {
                binding.btnAccept.background = mContext?.let {
                    ResourcesCompat.getDrawable(
                        it.resources,
                        R.drawable.button_blue_background, it.theme
                    )
                }
                binding.btnAccept.text = mContext?.getString(R.string.accept)
            }
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

        private val timerRunnable: Runnable = object : Runnable {
            override fun run() {
                timePassed += intervalTimeHandler
                if (timePassed < maxHandlerTime) {
                    timerHandler.postDelayed(this, intervalTimeHandler)
                    val timeRemaining: Long = maxHandlerTime - timePassed
                    binding.tvRemTime.text = DateUtils.secondsToMinutes(timeRemaining)
                    updateProgress(timeRemaining)
                } else {
                    invalidateViews()
                    binding.tvRemTime.text = "0 s"
                    showExpiredViews()
                }
            }
        }

        private fun showExpiredViews() {
            binding.btnAccept.background = mContext?.let {
                ResourcesCompat.getDrawable(
                    it.resources,
                    R.drawable.button_expired_background, it.theme
                )
            }
            binding.btnAccept.text = mContext?.getString(R.string.expired)
            setProgressView(5, 5, binding.llProgress)
        }

        private fun invalidateViews(){
            if (binding.llProgress.childCount > 0) {
                binding.llProgress.removeAllViews()
            }
        }

        private fun updateProgress(millisUntilFinished: Long){
            if (millisUntilFinished == 0L) return
            invalidateViews()
            val seconds = ceil((millisUntilFinished / 1000).toDouble()).toInt()
            when(floor((seconds / 60).toDouble()).toInt()){
                1 -> setProgressView(5, 4, binding.llProgress)
                2 -> setProgressView(5, 3, binding.llProgress)
                3 -> setProgressView(5, 2, binding.llProgress)
                4 -> setProgressView(5, 1, binding.llProgress)
                else -> {
                    if (seconds in 1..59){
                        setProgressView(5, 4, binding.llProgress)
                    }else {
                        setProgressView(5, 0, binding.llProgress)
                    }
                }
            }
            if (seconds == 0){
                showExpiredViews()
            }
        }
    }

interface OnAcceptExpireButtonClickListener{
    fun onAcceptExpireClicked(order: Order, isExpired: Boolean)
}


}