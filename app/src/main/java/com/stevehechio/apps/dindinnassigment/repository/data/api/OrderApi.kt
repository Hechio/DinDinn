package com.stevehechio.apps.dindinnassigment.repository.data.api

import com.stevehechio.apps.dindinnassigment.repository.data.model.Ingredient
import com.stevehechio.apps.dindinnassigment.repository.data.model.Order
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by stevehechio on 6/27/21
 */
interface OrderApi {
    @GET("order")
    fun getOrders(): Single<List<Order>>

    @GET("ingre")
    fun getIngredients(): Single<List<Ingredient>>

     @GET("ingre")
    fun getIngredients(@Query("id") id: Long): Single<List<Ingredient>>

}