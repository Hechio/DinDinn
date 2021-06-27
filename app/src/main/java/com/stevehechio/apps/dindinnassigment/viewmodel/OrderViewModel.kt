package com.stevehechio.apps.dindinnassigment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevehechio.apps.dindinnassigment.di.component.DaggerApiComponent
import com.stevehechio.apps.dindinnassigment.repository.data.api.NetworkService
import com.stevehechio.apps.dindinnassigment.repository.data.model.Order
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by stevehechio on 6/27/21
 */
class OrderViewModel: ViewModel() {
    @Inject
    lateinit var networkService: NetworkService

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    init {
        DaggerApiComponent.create().inject(this)
        getAllOrders()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun refresh(){
        getAllOrders()
    }

    private fun getAllOrders() {
        compositeDisposable.add(
            networkService.getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it }
                .subscribeWith(createOrderObserver())
        )
    }

    private fun createOrderObserver(): DisposableSingleObserver<in List<Order>> {
        return object : DisposableSingleObserver<List<Order>>(){
            override fun onSuccess(t: List<Order>) {
                inProgress.value = true
                isError.value = false
                orderList.value = t
                inProgress.value = false
            }

            override fun onError(e: Throwable) {
               inProgress.value = true
                isError.value = true
                Timber.e("Fetch order error ${e.message}")
                inProgress.value = false
            }

        }
    }

    private val orderList by lazy { MutableLiveData<List<Order>>() }
    val orderListLiveData: LiveData<List<Order>>
        get() = orderList

    private val inProgress by lazy { MutableLiveData<Boolean>() }
    val inProgressLiveData: LiveData<Boolean>
        get() = inProgress

    private val isError by lazy { MutableLiveData<Boolean>() }
    val isErrorLiveData: LiveData<Boolean>
        get() = isError
}