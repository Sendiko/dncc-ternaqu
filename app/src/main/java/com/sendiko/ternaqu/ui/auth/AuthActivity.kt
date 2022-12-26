package com.sendiko.ternaqu.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}