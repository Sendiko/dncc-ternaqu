package com.sendiko.ternaqu.ui.container

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}