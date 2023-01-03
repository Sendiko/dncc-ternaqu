package com.sendiko.ternaqu.repository.model

data class Product(
    val id: Int,
    val brand: String,
    val benefit: String,
    val price: Int,
    val storeId: Int,
    val url: String,
)
