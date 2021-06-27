package com.stevehechio.apps.dindinnassigment.di.component

import com.stevehechio.apps.dindinnassigment.di.module.ApiModule
import com.stevehechio.apps.dindinnassigment.repository.data.api.NetworkService
import com.stevehechio.apps.dindinnassigment.view.activities.OrderScreenActivity
import com.stevehechio.apps.dindinnassigment.view.adapters.OrderAdapter
import com.stevehechio.apps.dindinnassigment.viewmodel.OrderViewModel
import dagger.Component

/**
 * Created by stevehechio on 6/27/21
 */

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(networkService: NetworkService)

    fun inject(orderViewModel: OrderViewModel)

    fun inject(orderScreenActivity: OrderScreenActivity)

}