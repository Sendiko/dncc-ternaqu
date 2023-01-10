package com.sendiko.ternaqu.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val loginState = booleanPreferencesKey(name = "login_state")
    private val tokenAccess = stringPreferencesKey(name = "token_access")
    private val userIDKey = intPreferencesKey("user_id")
    private val usernameKey = stringPreferencesKey("username")
    private val isPremiumKey = stringPreferencesKey("is_premium")

    fun getPremiumStatus(): Flow<String> {
        return dataStore.data.map { key ->
            key[isPremiumKey] ?: ""
        }
    }

    suspend fun savePremiumStatus(isPremium: String) {
        dataStore.edit { key ->
            key[isPremiumKey] = isPremium
        }
    }

    fun getUsername(): Flow<String> {
        return dataStore.data.map { key ->
            key[usernameKey] ?: ""
        }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { key ->
            key[usernameKey] = username
        }
    }

    suspend fun logoutApp(){
        dataStore.edit { key ->
            key.clear()
        }
    }

    fun getUserID(): Flow<Int> {
        return dataStore.data.map { key ->
            key[userIDKey] ?: 0
        }
    }

    suspend fun saveUserID(userID: Int) {
        dataStore.edit { key ->
            key[userIDKey] = userID
        }
    }

    fun getTokenAccess(): Flow<String> {
        return dataStore.data.map { key ->
            key[tokenAccess] ?: ""
        }
    }

    suspend fun saveTokenAccess(token: String) {
        dataStore.edit { key ->
            key[tokenAccess] = token
        }
    }

    fun getLoginState(): Flow<Boolean> {
        return dataStore.data.map { key ->
            key[loginState] ?: false
        }
    }

    suspend fun setLoginState(isLoggedIn: Boolean) {
        dataStore.edit { key ->
            key[loginState] = isLoggedIn
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}