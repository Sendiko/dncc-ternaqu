package com.sendiko.ternaqu.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentUpgradeBinding
import com.sendiko.ternaqu.repository.AuthViewModel
import com.sendiko.ternaqu.repository.AuthViewModelFactory
import com.sendiko.ternaqu.repository.auth.AuthPreferences
import com.sendiko.ternaqu.ui.auth.dataStore

class UpgradeFragment : Fragment() {

    private lateinit var binding: FragmentUpgradeBinding

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
        binding = FragmentUpgradeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()

        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_upgradeFragment_to_profileFragment)
        }

        binding.cardView.setOnClickListener {
            authViewModel.savePremiumStatus("1")
            findNavController().navigate(R.id.action_upgradeFragment_to_profileFragment)
        }
        binding.cardView2.setOnClickListener {
            authViewModel.savePremiumStatus("1")
            findNavController().navigate(R.id.action_upgradeFragment_to_profileFragment)
        }
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_upgradeFragment_to_profileFragment)
        }
    }

}