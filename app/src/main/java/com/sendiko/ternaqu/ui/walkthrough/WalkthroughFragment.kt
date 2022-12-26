package com.sendiko.ternaqu.ui.walkthrough

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentWalkthroughBinding
import com.sendiko.ternaqu.ui.auth.AuthActivity

class WalkthroughFragment : Fragment() {

    private lateinit var binding : FragmentWalkthroughBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWalkthroughBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            AuthActivity.open(requireContext(), isLoggingIn = true)
        }
        binding.buttonRegister.setOnClickListener {
            AuthActivity.open(requireContext(), isLoggingIn = false)
        }

    }

}