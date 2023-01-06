package com.sendiko.ternaqu.network

import com.sendiko.ternaqu.network.request.LoginRequest
import com.sendiko.ternaqu.network.request.RegisterRequest
import com.sendiko.ternaqu.network.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    fun postLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("register")
    fun postRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @GET("recipe")
    fun getRecipe() : Call<RecipeResponse>

    @GET("product")
    fun getProduct() : Call<ProductResponse>

    @GET("topics")
    fun getTopics() : Call<TopicsResponse>

    @GET("topics/{id}")
    fun getTopic(
        @Path("id") id: String
    ) : Call<RepliesResponse>

}