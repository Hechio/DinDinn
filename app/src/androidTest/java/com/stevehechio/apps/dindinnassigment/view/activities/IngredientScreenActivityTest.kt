package com.stevehechio.apps.dindinnassigment.view.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by stevehechio on 6/29/21
 */
@RunWith(AndroidJUnit4::class)
class IngredientScreenActivityTest {
    @get:Rule
    val activityScenario = activityScenarioRule<IngredientScreenActivity>()

    @Test
    fun appLaunchesSuccessfully(){
        ActivityScenario.launch(IngredientScreenActivity::class.java)
    }
    @Before
    fun setUp() {
    }



    @After
    fun tearDown() {
    }
}