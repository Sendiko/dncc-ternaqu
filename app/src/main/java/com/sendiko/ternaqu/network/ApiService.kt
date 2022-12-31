package com.sendiko.ternaqu.network

import com.sendiko.ternaqu.network.request.LoginRequest
import com.sendiko.ternaqu.network.request.RegisterRequest
import com.sendiko.ternaqu.network.response.LoginResponse
import com.sendiko.ternaqu.network.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun postLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("register")
    fun postRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

}