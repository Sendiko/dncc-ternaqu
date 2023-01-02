package com.sendiko.ternaqu.repository.model

data class Recipe(
    val id : Int,
    val title : String,
    val benefits : String,
    val toolsAndMaterials : String,
    val steps : String,
    val imageUrl : String,
)
