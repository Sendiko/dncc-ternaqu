package com.sendiko.ternaqu.repository.model

data class Recipe(
    val id : Int,
    val title : String,
    val benefits : String,
    val tools_and_materials : String,
    val steps : String,
    val url : String,
)
