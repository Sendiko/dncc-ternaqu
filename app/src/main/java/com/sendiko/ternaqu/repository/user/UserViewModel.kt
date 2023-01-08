package com.sendiko.ternaqu.repository.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sendiko.ternaqu.network.request.LoginRequest
import com.sendiko.ternaqu.network.request.RegisterRequest
import com.sendiko.ternaqu.network.response.*
import com.sendiko.ternaqu.repository.helper.FailedMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "UserViewModel"

class UserViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = UserRepository(app)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailed = MutableLiveData<FailedMessage>()
    val isFailed: LiveData<FailedMessage> = _isFailed

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun postRegister(
        registerRequest: RegisterRequest
    ): LiveData<Boolean> {
        _isLoading.value = true
        val isSuccess = MutableLiveData<Boolean>()
        val request = repo.postRegister(registerRequest)
        request.enqueue(
            object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    _isLoading.value = false
                    when (response.code()) {
                        201 -> {
                            when {
                                response.body() == null -> {
                                    _isFailed.value = FailedMessage(true, "Server error.")
                                }
                                else -> isSuccess.value = true
                            }
                        }
                        422 -> {
                            _isFailed.value =
                                FailedMessage(true, "Register failed, please re-check the data")
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isFailed.value = FailedMessage(true, t.message.toString())
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            }
        )
        return isSuccess
    }

    fun postLogin(
        loginRequest: LoginRequest
    ): LiveData<String> {
        val resultToken = MutableLiveData<String>()
        _isLoading.value = true
        val request = repo.postLogin(loginRequest)
        request.enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _isLoading.value = false
                    when (response.code()) {
                        200 -> {
                            when {
                                response.body() != null -> {
                                    resultToken.value = response.body()!!.token!!
                                    _user.value = response.body()!!.user!!
                                }
                            }
                        }
                        404 -> {
                            _isFailed.value = FailedMessage(true, "Account not found")
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isFailed.value = FailedMessage(true, t.message.toString())
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            }
        )
        return resultToken
    }

    fun getUser(token: String): LiveData<UserResponse> {
        _isLoading.value = true
        val resultUser = MutableLiveData<UserResponse>()
        val request = repo.getUser("Bearer $token")
        request.enqueue(
            object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    when (response.code()) {
                        200 -> {
                            resultUser.value = response.body()!!
                        }
                        else -> {
                            _isFailed.value = FailedMessage(true, "Server error.")
                        }
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isFailed.value = FailedMessage(true, "Server error.")
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            }
        )
        return resultUser
    }

    fun postLogout(token: String): LiveData<Boolean> {
        _isLoading.value = true
        val isSuccess = MutableLiveData<Boolean>()
        val request = repo.postLogout("Bearer $token")
        request.enqueue(
            object : Callback<LogoutResponse> {
                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>
                ) {
                    _isLoading.value = false
                    when (response.code()) {
                        200 -> isSuccess.value = true
                        else -> _isFailed.value = FailedMessage(true, "Server error.")
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isFailed.value = FailedMessage(true, "Server error.")
                }

            }
        )
        return isSuccess
    }

}