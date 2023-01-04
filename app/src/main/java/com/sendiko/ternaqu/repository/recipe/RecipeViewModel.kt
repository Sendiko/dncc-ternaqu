package com.sendiko.ternaqu.repository.recipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sendiko.ternaqu.network.response.RecipeResponse
import com.sendiko.ternaqu.repository.model.FailedMessage
import com.sendiko.ternaqu.repository.model.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RecipeViewModel"
class RecipeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = RecipeRepository(app)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailed = MutableLiveData<FailedMessage>()
    val isFailed: LiveData<FailedMessage> = _isFailed

    fun getRecipe(): LiveData<ArrayList<Recipe>> {
        _isLoading.value = true
        val resultRecipe = MutableLiveData<ArrayList<Recipe>>()
        val recipeList = ArrayList<Recipe>()
        val request = repo.getRecipe()
        request.enqueue(
            object : Callback<RecipeResponse>{
                override fun onResponse(
                    call: Call<RecipeResponse>,
                    response: Response<RecipeResponse>
                ) {
                    when(response.code()){
                        200 -> {
                            when(response.body()){
                                null -> _isFailed.value = FailedMessage(true, "Server error.")
                                else -> {
                                    for(i in response.body()!!.recipe!!){
                                        when(i){
                                            null -> _isFailed.value = FailedMessage(true, "Data empty.")
                                            else -> {
                                                val recipe = Recipe(
                                                    i.id?:0,
                                                    i.title?:"",
                                                    i.benefit?:"",
                                                    i.toolsAndMaterials?:"",
                                                    i.steps?:"",
                                                    i.imageUrl?:""
                                                    )
                                                _isLoading.value = false
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
                    _isFailed.value = FailedMessage(true, "${t.message}")
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            }

        )
        return resultRecipe
    }

}