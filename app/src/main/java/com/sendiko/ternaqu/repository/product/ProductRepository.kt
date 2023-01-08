package com.sendiko.ternaqu.repository.product

import android.app.Application
import com.sendiko.ternaqu.network.NetworkConfig

class ProductRepository(app : Application) {

    private val client = NetworkConfig.getInstance(app)

    fun getProducts() = client.getProducts()

    fun getProduct(id: String) = client.getProduct(id)

}