package com.sendiko.ternaqu.repository.recipe

import android.app.Application
import com.sendiko.ternaqu.network.NetworkConfig
import com.sendiko.ternaqu.repository.model.Recipe

class RecipeRepository(app : Application) {

    private val client = NetworkConfig.getInstance(app.applicationContext)

    fun getRecipe() = client.getRecipe()

}