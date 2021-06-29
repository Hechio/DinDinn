package com.stevehechio.apps.dindinnassigment.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.stevehechio.apps.dindinnassigment.R
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
        val textView: TextView =
        search_view_ingre.findViewById(R.id.search_src_text)
        textView.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, theme))
        textView.setHintTextColor((ResourcesCompat.getColor(resources, R.color.grayCal, theme)))
        val imvSearch: ImageView =
            search_view_ingre.findViewById(R.id.search_mag_icon)
        imvSearch.setImageResource(R.drawable.ic_round_search_24)
    val imvClose: ImageView =
            search_view_ingre.findViewById(R.id.search_close_btn)
        imvClose.setImageResource(R.drawable.ic_baseline_close_24)
        search_view_ingre.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.trim().isEmpty()){
                    clearFragments()
                    ingredientViewModel.refresh()
                    observeOrderLiveData(false)

                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                observeOrderLiveData(true)
                val id = query.toIntOrNull() ?: 0
                if (id > 0){
                    ingredientViewModel.getIngredientById(id.toLong())
                }else {
                    Toast.makeText(applicationContext,"Please enter a numeric value",Toast.LENGTH_SHORT).show()
                }
                return true
            }

        })

        sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager,lifecycle)
        view_pager.adapter = sectionPagerAdapter
        observeOrderLiveData(false)
    }
    private fun observeOrderLiveData(isSearch: Boolean) {
        observeInProgress(isSearch)
        observeIsError(isSearch)
        observeOrderList(isSearch)
    }


    private fun observeIsError(isSearch: Boolean) {
        if (isSearch){
            ingredientViewModel.isSearchErrorLiveData.observe(this, {
                    isError -> isError.let {
                ingre_fetch_error.visibility = if (it) View.VISIBLE else View.GONE
            }
            })
        }else {
            ingredientViewModel.isErrorLiveData.observe(this, {
                    isError -> isError.let {
                ingre_fetch_error.visibility = if (it) View.VISIBLE else View.GONE
            }
            })
        }

    }


    private fun observeInProgress(isSearch: Boolean) {
        if (isSearch){
            ingredientViewModel.inSearchProgressLiveData.observe(this, {
                    isLoading -> isLoading.let {
                if (it){
                    ingre_fetch_error.gone()
                    ingre_fetch_progress.visible()
                }else {
                    ingre_fetch_progress.gone()
                }
            }
            })
        }else{
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

    }


    private fun observeOrderList(isSearch: Boolean) {
        clearFragments()
        if (isSearch){
            ingredientViewModel.ingredientSearchListLiveData.observe(this, {
                    ingreMap ->
                clearFragments()
                val keysArray = ingreMap.keys.toList()
                keysArray.forEach {
                    ingreMap[it]?.let { it1 ->
                        IngredientFragment.newInstance(
                            it1
                        )
                    }?.let { it2 -> sectionPagerAdapter?.addFragment(it2) }
                }

                TabLayoutMediator(tab_layout,view_pager){tab, pos ->
                    tab.text = keysArray[0]
                }.attach()
                view_pager.adapter?.notifyItemChanged(0)
            })
        }else{
            ingredientViewModel.ingredientListLiveData.observe(this, {
                    ingreMap ->
                clearFragments()
                val keysArray = ingreMap.keys.toList()
                keysArray.forEach {
                    ingreMap[it]?.let { it1 ->
                        IngredientFragment.newInstance(
                            it1
                        )
                    }?.let { it2 ->  sectionPagerAdapter?.addFragment(it2)}
                }
                TabLayoutMediator(tab_layout,view_pager){tab, pos ->
                    //view_pager.setCurrentItem(tab.position,true)
                    tab.text = keysArray[pos]
                }.attach()
                view_pager.adapter?.notifyItemChanged(0)
            })
        }


    }

    private fun clearFragments() {
        tab_layout.removeAllTabs()
        sectionPagerAdapter?.clearFragments()
    }

}
