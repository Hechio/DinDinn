package com.stevehechio.apps.dindinnassigment.repository.data.api

import com.stevehechio.apps.dindinnassigment.di.component.DaggerApiComponent
import com.stevehechio.apps.dindinnassigment.repository.data.model.Ingredient
import com.stevehechio.apps.dindinnassigment.repository.data.model.Order
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by stevehechio on 6/27/21
 */
class NetworkService {

    @Inject
    lateinit var orderApi: OrderApi


    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getOrders(): Single<List<Order>> {
        return orderApi.getOrders()
    }

    fun getIngredients(): Single<List<Ingredient>>{
        return orderApi.getIngredients()
    }

    fun searchIngredient(id: Long): Single<List<Ingredient>>{
        return orderApi.getIngredients(id)
    }

    companion object {
        val BASE_URL =  "https://60d6e2fd307c300017a5f556.mockapi.io/hechio/api/vi/"
    }
}