package com.stevehechio.apps.dindinnassigment.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stevehechio.apps.dindinnassigment.databinding.ActivityIngredientScreenBinding

class IngredientScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIngredientScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientScreenBinding.inflate(layoutInflater)
        setUpViews()
        setContentView(binding.root)
    }

    private fun setUpViews() {
    }


}