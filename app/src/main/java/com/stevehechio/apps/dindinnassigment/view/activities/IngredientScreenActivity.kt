package com.stevehechio.apps.dindinnassigment.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.stevehechio.apps.dindinnassigment.databinding.ActivityIngredientScreenBinding
import com.stevehechio.apps.dindinnassigment.di.component.DaggerApiComponent
import com.stevehechio.apps.dindinnassigment.utils.gone
import com.stevehechio.apps.dindinnassigment.utils.visible
import com.stevehechio.apps.dindinnassigment.view.adapters.SectionPagerAdapter
import com.stevehechio.apps.dindinnassigment.view.fragments.IngredientFragment
import com.stevehechio.apps.dindinnassigment.viewmodel.IngredientViewModel
import kotlinx.android.synthetic.main.activity_ingredient_screen.*

class IngredientScreenActivity : AppCompatActivity() {
    private var sectionPagerAdapter: SectionPagerAdapter? = null
    private lateinit var binding : ActivityIngredientScreenBinding
    private val ingredientViewModel: IngredientViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
    DaggerApiComponent.create().inject(this)
        sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager,lifecycle)
        view_pager.adapter = sectionPagerAdapter
        observeOrderLiveData()
    }
    private fun observeOrderLiveData() {
        observeInProgress()
        observeIsError()
        observeOrderList()
    }

    private fun observeIsError() {
        ingredientViewModel.isErrorLiveData.observe(this, {
                isError -> isError.let {
            ingre_fetch_error.visibility = if (it) View.VISIBLE else View.GONE
        }
        })
    }


    private fun observeInProgress() {
        ingredientViewModel.inProgressLiveData.observe(this, {
                isLoading -> isLoading.let {
            if (it){
                ingre_fetch_error.gone()
                ingre_fetch_progress.visible()
            }else {
                ingre_fetch_progress.gone()
            }
        }
        })
    }


    private fun observeOrderList() {
        ingredientViewModel.ingredientListLiveData.observe(this, {
           ingreMap ->
            val keysArray = ingreMap.keys.toList()
            keysArray.forEach {
                ingreMap[it]?.let { it1 ->
                    IngredientFragment.newInstance(
                        it1
                    )
                }?.let { it2 -> sectionPagerAdapter?.addFragment(it2) }
            }
            TabLayoutMediator(tab_layout,view_pager){tab, pos ->
                tab.text = keysArray[pos]
            }.attach()
        })

    }

}