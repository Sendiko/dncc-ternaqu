package com.sendiko.ternaqu.network

import com.sendiko.ternaqu.network.request.LoginRequest
import com.sendiko.ternaqu.network.request.RegisterRequest
import com.sendiko.ternaqu.network.request.TopicRequest
import com.sendiko.ternaqu.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("login")
    fun postLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("register")
    fun postRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @GET("user")
    fun getUser(
        @Header("Authorization") token: String
    ) : Call<UserResponse>

    @GET("recipe")
    fun getRecipe() : Call<RecipeResponse>

    @GET("product")
    fun getProducts() : Call<ProductsResponse>

    @GET("product/{id}")
    fun getProduct(
        @Path("id") id: String
    ) : Call<ProductResponse>

    @GET("topics")
    fun getTopics() : Call<TopicsResponse>

    @GET("topics/{id}")
    fun getTopic(
        @Path("id") id: String
    ) : Call<RepliesResponse>

    @POST("topics")
    fun postTopic(
        @Header("Authorization") token: String,
        @Body topicRequest: TopicRequest
    ): Call<TopicResponse>

}