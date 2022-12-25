package com.sendiko.ternaqu.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)

        Handler(Looper.getMainLooper()).postDelayed({
            this.findNavController().navigate(R.id.action_splashScreenFragment2_to_walkthroughFragment)
        }, 800)

        return binding.root
    }
}