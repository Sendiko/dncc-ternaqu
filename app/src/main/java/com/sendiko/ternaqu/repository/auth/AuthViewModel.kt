package com.sendiko.ternaqu.repository

import androidx.lifecycle.*
import com.sendiko.ternaqu.repository.auth.AuthPreferences
import kotlinx.coroutines.launch

class AuthViewModel(private val auth: AuthPreferences) : ViewModel() {

    fun getPremiumStatus(): LiveData<String> {
        return auth.getPremiumStatus().asLiveData()
    }

    fun savePremiumStatus(isPremium: String) {
        viewModelScope.launch {
            auth.savePremiumStatus(isPremium)
        }
    }

    fun getUsername(): LiveData<String> {
        return auth.getUsername().asLiveData()
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            auth.saveUsername(username)
        }
    }

    fun logoutApp(){
        viewModelScope.launch {
            auth.logoutApp()
        }
    }

    fun getUserID(): LiveData<Int> {
        return auth.getUserID().asLiveData()
    }

    fun saveUserID(userID: Int) {
        viewModelScope.launch {
            auth.saveUserID(userID)
        }
    }

    fun getTokenAccess(): LiveData<String> {
        return auth.getTokenAccess().asLiveData()
    }

    fun saveTokenAccess(token: String) {
        viewModelScope.launch {
            auth.saveTokenAccess(token)
        }
    }

    fun getLoginState(): LiveData<Boolean> {
        return auth.getLoginState().asLiveData()
    }

    fun setLoginState(isLoggedIn: Boolean) {
        viewModelScope.launch {
            auth.setLoginState(isLoggedIn)
        }
    }

}

class AuthViewModelFactory(private val auth: AuthPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> return AuthViewModel(auth) as T
            else -> throw IllegalArgumentException("Unknown model class : " + modelClass.name)
        }
    }
}