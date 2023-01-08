package com.sendiko.ternaqu.repository.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sendiko.ternaqu.network.response.ProductItem
import com.sendiko.ternaqu.network.response.RecipeItem
import com.sendiko.ternaqu.network.response.TopicsItem

class SharedViewModel: ViewModel() {

    private val _topic = MutableLiveData<TopicsItem>()
    val topic: LiveData<TopicsItem> = _topic

    fun saveTopic(topic: TopicsItem){
        _topic.value = topic
    }

    private val _recipe = MutableLiveData<RecipeItem>()
    val recipe: LiveData<RecipeItem> = _recipe

    fun saveRecipe(recipe: RecipeItem){
        _recipe.value = recipe
    }

    private val _product = MutableLiveData<ProductItem>()
    val product: LiveData<ProductItem> = _product

    fun saveProduct(product: ProductItem){
        _product.value = product
    }

}