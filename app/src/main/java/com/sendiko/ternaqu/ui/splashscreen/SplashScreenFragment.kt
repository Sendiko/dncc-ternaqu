package com.sendiko.ternaqu.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentSplashScreenBinding
import com.sendiko.ternaqu.repository.AuthViewModel
import com.sendiko.ternaqu.repository.AuthViewModelFactory
import com.sendiko.ternaqu.repository.auth.AuthPreferences
import com.sendiko.ternaqu.ui.auth.dataStore
import com.sendiko.ternaqu.ui.container.MainActivity

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    private val pref by lazy {
        AuthPreferences.getInstance(requireNotNull(this.context).dataStore)
    }

    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider(this, AuthViewModelFactory(pref))[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.getLoginState().observe(viewLifecycleOwner){
            when(it){
                true -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        requireContext().startActivity(Intent(context, MainActivity::class.java))
                    }, 800)
                }
                else -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        this.findNavController().navigate(R.id.action_splashScreenFragment2_to_walkthroughFragment)
                    }, 800)
                }
            }
        }

    }

}