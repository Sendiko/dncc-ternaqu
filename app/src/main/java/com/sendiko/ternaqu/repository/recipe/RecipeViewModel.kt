package com.sendiko.ternaqu.repository.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sendiko.ternaqu.network.response.RecipeItem
import com.sendiko.ternaqu.network.response.RecipeResponse
import com.sendiko.ternaqu.repository.helper.FailedMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = RecipeRepository(app)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailed = MutableLiveData<FailedMessage>()
    val isFailed: LiveData<FailedMessage> = _isFailed

    fun getRecipe(): LiveData<ArrayList<RecipeItem>> {
        _isLoading.value = true
        val resultRecipe = MutableLiveData<ArrayList<RecipeItem>>()
        val recipeList = ArrayList<RecipeItem>()
        val request = repo.getRecipe()
        request.enqueue(
            object : Callback<RecipeResponse>{
                override fun onResponse(
                    call: Call<RecipeResponse>,
                    response: Response<RecipeResponse>
                ) {
                    _isLoading.value = false
                    when(response.code()){
                        200 -> {
                            when(response.body()){
                                null -> _isFailed.value = FailedMessage(true, "Server error.")
                                else -> {
                                    for(i in response.body()!!.recipe!!){
                                        when(i){
                                            null -> _isFailed.value = FailedMessage(true, "Data empty.")
                                            else -> {
                                                val recipe = RecipeItem(
                                                    i.id,
                                                    i.title,
                                                    i.benefit,
                                                    i.steps,
                                                    i.toolsAndMaterials,
                                                    i.imageUrl
                                                    )
                                                recipeList.add(recipe)
                                                resultRecipe.value = recipeList
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else -> {
                            _isFailed.value = FailedMessage(true, "Server error.")
                        }
                    }
                }

                override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isFailed.value = FailedMessage(true, "Server error.")
                }
            }

        )
        return resultRecipe
    }

}