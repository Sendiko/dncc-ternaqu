package com.sendiko.ternaqu.repository.user

import android.app.Application
import com.sendiko.ternaqu.network.NetworkConfig
import com.sendiko.ternaqu.network.request.LoginRequest
import com.sendiko.ternaqu.network.request.RegisterRequest

class UserRepository(app: Application) {

    private val client = NetworkConfig.getInstance(app.applicationContext)

    fun postLogin(loginRequest: LoginRequest) = client.postLogin(loginRequest)

    fun postRegister(registerRequest: RegisterRequest) = client.postRegister(registerRequest)

    fun postLogout(token: String) = client.postLogout(token)

    fun getUser(token: String) = client.getUser(token)

    fun upgradeToPremium(id: String, token: String) = client.upgradeToPremium(id, token)

}