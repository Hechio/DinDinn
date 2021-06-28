package com.stevehechio.apps.dindinnassigment.viewmodel.model

import com.stevehechio.apps.dindinnassigment.repository.data.model.Ingredient
import java.io.Serializable

/**
 * Created by stevehechio on 6/28/21
 */
data class IngreCategory(var category: String, var ingredientList: List<Ingredient>): Serializable