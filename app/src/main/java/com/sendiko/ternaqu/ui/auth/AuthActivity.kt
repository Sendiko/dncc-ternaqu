package com.sendiko.ternaqu.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.ActivityAuthBinding

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "auth_pref")
class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    companion object {
        private const val IS_LOGGING_IN = "isLoggingIn"
        fun open(
            context: Context,
            isLoggingIn : Boolean
        ){
            context.startActivity(
                Intent(
                    context, AuthActivity::class.java
                ).apply {
                    putExtra(IS_LOGGING_IN, isLoggingIn)
                })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.auth_nav)
        val navController = navHostFragment.navController
        val destination = when {
            intent.getBooleanExtra(IS_LOGGING_IN, true) -> R.id.loginFragment
            else -> R.id.registerFragment
        }

        navGraph.setStartDestination(destination)
        navController.setGraph(navGraph, intent.extras)

    }

}