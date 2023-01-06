package com.sendiko.ternaqu.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val loginState = booleanPreferencesKey(name = "login_state")
    private val tokenAccess = stringPreferencesKey(name = "token_access")

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