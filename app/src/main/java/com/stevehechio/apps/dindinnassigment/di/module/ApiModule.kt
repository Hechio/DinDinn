package com.stevehechio.apps.dindinnassigment.di.module

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.stevehechio.apps.dindinnassigment.repository.data.api.NetworkService
import com.stevehechio.apps.dindinnassigment.repository.data.api.OrderApi
import com.stevehechio.apps.dindinnassigment.repository.data.model.Addon
import com.stevehechio.apps.dindinnassigment.repository.data.model.Ingredient
import com.stevehechio.apps.dindinnassigment.repository.data.model.Order
import com.stevehechio.apps.dindinnassigment.view.adapters.IngredientAdapter
import com.stevehechio.apps.dindinnassigment.view.adapters.OrderAdapter
import com.stevehechio.apps.dindinnassigment.view.adapters.OrderAddonAdapter
import com.stevehechio.apps.dindinnassigment.view.adapters.SectionPagerAdapter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by stevehechio on 6/27/21
 */
@Module
class ApiModule {

 @Provides
 fun provideOrderApi(): OrderApi {
     return Retrofit.Builder()
         .baseUrl(NetworkService.BASE_URL)
         .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
         .addConverterFactory(MoshiConverterFactory.create())
         .build()
         .create(OrderApi::class.java)
 }
    @Provides
     fun provideNetworkService(): NetworkService {
        return NetworkService()
    }
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return  CompositeDisposable()
    }
    @Provides
    fun provideOrderList(): List<Order> {
        return ArrayList()
    }
    @Provides
    fun provideOrderAdapter(values: List<Order>?): OrderAdapter {
       return OrderAdapter(values)
    }

    @Provides
    fun provideIngredientList(): List<Ingredient> {
        return emptyList()
    }

    @Provides
    fun provideIngredientAdapter(values: List<Ingredient>?): IngredientAdapter{
        return IngredientAdapter(values)
    }

}