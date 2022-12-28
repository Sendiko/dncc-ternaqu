package com.sendiko.ternaqu.repository

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class AuthViewModel(private val auth : AuthPreferences) : ViewModel() {

    fun getLoginState() : LiveData<Boolean> {
        return auth.getLoginState().asLiveData()
    }

    fun setLoginState(isLoggedIn : Boolean){
        viewModelScope.launch {
            auth.setLoginState(isLoggedIn)
        }
    }

}

class AuthViewModelFactory(private val auth : AuthPreferences) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> return AuthViewModel(auth) as T
            else -> throw IllegalArgumentException("Unknown model class : " + modelClass.name)
        }
    }
}