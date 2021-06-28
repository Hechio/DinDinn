package com.stevehechio.apps.dindinnassigment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevehechio.apps.dindinnassigment.di.component.DaggerApiComponent
import com.stevehechio.apps.dindinnassigment.repository.data.api.NetworkService
import com.stevehechio.apps.dindinnassigment.repository.data.model.Ingredient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.RuntimeException
import javax.inject.Inject

/**
 * Created by stevehechio on 6/28/21
 */
class IngredientViewModel: ViewModel() {
    @Inject
    lateinit var networkService: NetworkService

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var compositeDisposable2: CompositeDisposable
    init {
        DaggerApiComponent.create().inject(this)
        getAllIngredients()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable2.clear()
    }


    fun refresh(){
        getAllIngredients()
    }

    private fun getAllIngredients() {
        compositeDisposable.add(
            networkService.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it }
                .subscribeWith(createIngredientObserver())
        )
    }

    fun getIngredientById(id: Long){
        compositeDisposable2.add(
            networkService.searchIngredient(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it }
                .subscribeWith(createIngredientSearchObserver())
        )
    }

    private fun createIngredientObserver(): DisposableSingleObserver<List<Ingredient>> {
        return object : DisposableSingleObserver<List<Ingredient>>(){
            override fun onSuccess(t: List<Ingredient>) {
                val groupByCategory = t.groupBy { it.category }
                inProgress.value = true
                isError.value = false
                ingredientList.value = groupByCategory
                inProgress.value = false
            }

            override fun onError(e: Throwable) {
                inProgress.value = true
                isError.value = true
                Timber.e("Fetch ingre error ${e.message}")
                inProgress.value = false
            }

        }
    }
     private fun createIngredientSearchObserver(): DisposableSingleObserver<List<Ingredient>> {
        return object : DisposableSingleObserver<List<Ingredient>>(){
            override fun onSuccess(t: List<Ingredient>) {
                val groupByCategory = t.groupBy { it.category }
                inSearchProgress.value = true
                isSearchError.value = false
                ingredientSearchList.value = groupByCategory
                inSearchProgress.value = false
            }

            override fun onError(e: Throwable) {
                inSearchProgress.value = true
                isSearchError.value = true
                Timber.e("Fetch ingre error ${e.message}")
                inSearchProgress.value = false
            }

        }
    }



    private val ingredientList by lazy { MutableLiveData< Map<String, List<Ingredient>>>() }
    val ingredientListLiveData: LiveData< Map<String, List<Ingredient>>>
        get() = ingredientList

    private val inProgress by lazy { MutableLiveData<Boolean>() }
    val inProgressLiveData: LiveData<Boolean>
        get() = inProgress

    private val isError by lazy { MutableLiveData<Boolean>() }
    val isErrorLiveData: LiveData<Boolean>
        get() = isError

    private val ingredientSearchList by lazy { MutableLiveData< Map<String, List<Ingredient>>>() }
    val ingredientSearchListLiveData: LiveData< Map<String, List<Ingredient>>>
        get() = ingredientSearchList

    private val inSearchProgress by lazy { MutableLiveData<Boolean>() }
    val inSearchProgressLiveData: LiveData<Boolean>
        get() = inSearchProgress

    private val isSearchError by lazy { MutableLiveData<Boolean>() }
    val isSearchErrorLiveData: LiveData<Boolean>
        get() = isSearchError



}