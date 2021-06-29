package com.stevehechio.apps.dindinnassigment.repository.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stevehechio.apps.dindinnassigment.enqueueResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.AfterClass
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by stevehechio on 6/29/21
 */
class NetworkServiceTest {

    private val mockWebServer = MockWebServer()
    @get:Rule
    val instant = InstantTaskExecutorRule()

    val orderApi= Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(OrderApi::class.java)

    @Test
    fun `fetch Orders with response 200`() {
        mockWebServer.enqueueResponse("orders.json",200)
        val orders=orderApi.getOrders().blockingGet()
        assert(orders.isNotEmpty())
    }

    @Test
    fun `fetch all ingredients with response 200`() {
        mockWebServer.enqueueResponse("ingredients.json",200)
        val orders=orderApi.getIngredients().blockingGet()
        assert(orders.isNotEmpty())
    }

    @Test
    fun `fetch ingredient by id with response 200`() {
        mockWebServer.enqueueResponse("ingredients.json",200)
        val orders=orderApi.getIngredients(12).blockingGet()
        assert(orders.isNotEmpty())
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

}