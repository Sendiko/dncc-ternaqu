package com.sendiko.ternaqu.repository.product

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sendiko.ternaqu.network.response.ProductResponse
import com.sendiko.ternaqu.repository.model.FailedMessage
import com.sendiko.ternaqu.repository.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ProductViewModel"

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ProductRepository(app)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailed = MutableLiveData<FailedMessage>()
    val isFailed: LiveData<FailedMessage> = _isFailed

    fun getProduct(): LiveData<ArrayList<Product>> {
        _isLoading.value = false
        val resultRecipe = MutableLiveData<ArrayList<Product>>()
        val recipeList = ArrayList<Product>()
        val request = repo.getProduct()
        request.enqueue(
            object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    _isLoading.value = false
                    when (response.code()) {
                        200 -> {
                            for (i in response.body()!!.product!!) {
                                when (i) {
                                    null -> {}
                                    else -> {
                                        val product = Product(
                                            i.id ?: 0,
                                            i.brand ?: "",
                                            i.benefits ?: "",
                                            i.price ?: 0,
                                            i.storeId ?: 0,
                                            i.imageUrl ?: ""
                                        )
                                        recipeList.add(product)
                                        resultRecipe.value = recipeList
                                    }
                                }
                            }
                        }
                        else -> {
                            _isFailed.value = FailedMessage(
                                true,
                                "Server error."
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isFailed.value = FailedMessage(true, "${t.message}")
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            }
        )
        return resultRecipe
    }

}