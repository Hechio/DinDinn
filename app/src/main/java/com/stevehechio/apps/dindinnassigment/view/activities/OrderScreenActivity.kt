package com.stevehechio.apps.dindinnassigment.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stevehechio.apps.dindinnassigment.databinding.ActivityOrderScreenBinding
import com.stevehechio.apps.dindinnassigment.di.component.DaggerApiComponent
import com.stevehechio.apps.dindinnassigment.repository.data.model.Order
import com.stevehechio.apps.dindinnassigment.utils.gone
import com.stevehechio.apps.dindinnassigment.utils.visible
import com.stevehechio.apps.dindinnassigment.view.adapters.OrderAdapter
import com.stevehechio.apps.dindinnassigment.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_order_screen.*
import javax.inject.Inject

class OrderScreenActivity : AppCompatActivity() {
    @Inject
    lateinit var orderAdapter: OrderAdapter
    private val orderViewModel: OrderViewModel by viewModels()
    private lateinit var binding: ActivityOrderScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()

    }

    private fun setUpViews() {
        DaggerApiComponent.create().inject(this)
        swipe_refresh_orders.setOnRefreshListener {
            swipe_refresh_orders.isRefreshing = false
            orderViewModel.refresh()
        }

        order_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = orderAdapter
        }

        observeOrderLiveData()
        ib_to_ingredients.setOnClickListener { startActivity(Intent(this,IngredientScreenActivity::class.java)) }
        orderAdapter.setOnAcceptExpireButtonListener(object : OrderAdapter.OnAcceptExpireButtonClickListener {
            override fun onAcceptExpireClicked(order: Order, isExpired: Boolean) {
                val newArrayList = orderAdapter.getOrderList()
                 newArrayList.remove(order)
                orderAdapter.setOrderList(newArrayList)
                val id = order.id
                if (isExpired){
                    Toast.makeText(applicationContext,"Order #$id moved to expired tab",Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(applicationContext,"Order #$id moved to processing tab",Toast.LENGTH_SHORT).show()
                }

            }

        })
    }

    private fun observeOrderLiveData() {
        observeInProgress()
        observeIsError()
        observeOrderList()
    }

    private fun observeIsError() {
        orderViewModel.isErrorLiveData.observe(this, {
            isError -> isError.let {
                order_fetch_error.visibility = if (it) View.VISIBLE else View.GONE
        }
        })
    }


    private fun observeInProgress() {
        orderViewModel.inProgressLiveData.observe(this, {
            isLoading -> isLoading.let {
                if (it){
                    order_fetch_error.gone()
                    order_recycler_view.gone()
                    order_fetch_progress.visible()
                }else {
                    order_fetch_progress.gone()
                }
        }
        })
    }


    private fun observeOrderList() {
        orderViewModel.orderListLiveData.observe(this, {
            allOrders -> allOrders.let {
            order_recycler_view.visible()
            orderAdapter.setOrder(it,this)
            }
        })
    }
}