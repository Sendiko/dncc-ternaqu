package com.sendiko.ternaqu.repository.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sendiko.ternaqu.network.response.ProductItem
import com.sendiko.ternaqu.network.response.ProductResponse
import com.sendiko.ternaqu.network.response.ProductsResponse
import com.sendiko.ternaqu.network.response.Store
import com.sendiko.ternaqu.repository.helper.FailedMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ProductRepository(app)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailed = MutableLiveData<FailedMessage>()
    val isFailed: LiveData<FailedMessage> = _isFailed

    fun getProducts(): LiveData<ArrayList<ProductItem>> {
        _isLoading.value = false
        val resultRecipe = MutableLiveData<ArrayList<ProductItem>>()
        val recipeList = ArrayList<ProductItem>()
        val request = repo.getProducts()
        request.enqueue(
            object : Callback<ProductsResponse> {
                override fun onResponse(
                    call: Call<ProductsResponse>,
                    response: Response<ProductsResponse>
                ) {
                    _isLoading.value = false
                    when (response.code()) {
                        200 -> {
                            for (i in response.body()!!.product!!) {
                                when (i) {
                                    null -> {}
                                    else -> {
                                        val product = ProductItem(
                                            i.id,
                                            i.brand,
                                            i.brand,
                                            i.description,
                                            i.benefits,
                                            i.price,
                                            i.storeId,
                                            i.productId,
                                            i.imageUrl
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

                override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isFailed.value = FailedMessage(true, "Server error.")
                }

            }
        )
        return resultRecipe
    }

    fun getProduct(id: String): LiveData<Store> {
        _isLoading.value = true
        val resultStore = MutableLiveData<Store>()
        val request = repo.getProduct(id)
        request.enqueue(
            object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    _isLoading.value = false
                    when (response.code()) {
                        200 -> {
                            resultStore.value = response.body()!!.store!!
                        }
                        else -> {
                            _isFailed.value = FailedMessage(true, "Server error.")
                        }
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isFailed.value = FailedMessage(true, "Server error.")
                }

            }
        )
        return resultStore
    }

}