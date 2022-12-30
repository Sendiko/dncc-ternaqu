package com.sendiko.ternaqu.repository

import com.sendiko.ternaqu.repository.model.Product

class ProductRepository {
    fun getProduct() : ArrayList<Product> {
        return arrayListOf(
            Product(
                1,
                "Product one",
                "this",
                100000,
                "ini toko",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmGqdd03oMQCUuePwFyXPexg_AtcVefshUgA&usqp=CAU"
            ),
            Product(
                2,
                "Product two",
                "this",
                200000,
                "ini toko",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmGqdd03oMQCUuePwFyXPexg_AtcVefshUgA&usqp=CAU"
            ),
            Product(
                3,
                "Product three",
                "this",
                300000,
                "ini toko",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmGqdd03oMQCUuePwFyXPexg_AtcVefshUgA&usqp=CAU"
            ),
        )
    }
}