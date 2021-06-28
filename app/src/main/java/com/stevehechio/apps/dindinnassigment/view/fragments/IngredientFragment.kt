package com.stevehechio.apps.dindinnassigment.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.stevehechio.apps.dindinnassigment.R
import com.stevehechio.apps.dindinnassigment.databinding.FragmentIngredientBinding
import com.stevehechio.apps.dindinnassigment.di.component.DaggerApiComponent
import com.stevehechio.apps.dindinnassigment.repository.data.model.Ingredient
import com.stevehechio.apps.dindinnassigment.utils.gone
import com.stevehechio.apps.dindinnassigment.utils.visible
import com.stevehechio.apps.dindinnassigment.view.adapters.IngredientAdapter
import kotlinx.android.synthetic.main.fragment_ingredient.*
import javax.inject.Inject

class IngredientFragment : Fragment() {
    private var ingredientList: List<Ingredient>? = null
    private val binding get() = _binding!!
    private var _binding: FragmentIngredientBinding? = null

    @Inject
    lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientBinding.inflate(inflater,container,false)
        val view = binding.root
        setUpViews()
        return view
    }

    private fun setUpViews() {
        DaggerApiComponent.create().inject(this)
        binding.rvIngredients.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL,false)
            adapter = ingredientAdapter
        }
        ingredientAdapter.setIngredients(ingredientList,requireContext())
        binding.pbFetchProgress.gone()
        if (ingredientAdapter.itemCount < 1){
            binding.tvFetchError.visible()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {

        @JvmStatic
        fun newInstance(ingredientList: List<Ingredient>) =
            IngredientFragment().apply {
                this.ingredientList = ingredientList
            }
    }
}