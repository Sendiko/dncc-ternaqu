package com.sendiko.ternaqu.ui.container

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}