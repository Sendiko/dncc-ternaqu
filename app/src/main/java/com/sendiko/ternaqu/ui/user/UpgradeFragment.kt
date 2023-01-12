package com.sendiko.ternaqu.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentUpgradeBinding
import com.sendiko.ternaqu.repository.AuthViewModel
import com.sendiko.ternaqu.repository.AuthViewModelFactory
import com.sendiko.ternaqu.repository.auth.AuthPreferences
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.user.UserViewModel
import com.sendiko.ternaqu.ui.auth.dataStore
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

class UpgradeFragment : Fragment() {

    private lateinit var binding: FragmentUpgradeBinding

    private val pref by lazy {
        AuthPreferences.getInstance(requireNotNull(this.context).dataStore)
    }

    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider(this, AuthViewModelFactory(pref))[AuthViewModel::class.java]
    }

    private fun obtainViewModel(activity: FragmentActivity): UserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private val userViewModel by lazy {
        obtainViewModel(requireNotNull(this.activity))
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

        userViewModel.isFailed.observe(viewLifecycleOwner) {
            when {
                it.isFailed -> showSnackbar(it.failedMessage)
            }
        }

        userViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_upgradeFragment_to_profileFragment)
        }

        binding.cardView.setOnClickListener {
            authViewModel.getUserID().observe(viewLifecycleOwner){ userId ->
                authViewModel.getTokenAccess().observe(viewLifecycleOwner){ token ->
                    userViewModel.upgradeToPremium(userId.toString(), token).observe(viewLifecycleOwner){ response ->
                        when (response.status) {
                            200 -> {
                                findNavController().navigate(R.id.action_upgradeFragment_to_profileFragment)
                                showSnackbar(response.message!!)
                            }
                        }
                    }
                }
            }
        }
        binding.cardView2.setOnClickListener {
            authViewModel.getUserID().observe(viewLifecycleOwner){ userId ->
                authViewModel.getTokenAccess().observe(viewLifecycleOwner){ token ->
                    userViewModel.upgradeToPremium(userId.toString(), token).observe(viewLifecycleOwner){ response ->
                        when (response.status) {
                            200 -> {
                                findNavController().navigate(R.id.action_upgradeFragment_to_profileFragment)
                                showSnackbar(response.message!!)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) {
        var loadingDialogFragment = LoadingDialogFragment()
        when {
            isLoading -> {
                loadingDialogFragment.show(parentFragmentManager)
            }
            else -> {
                loadingDialogFragment =
                    parentFragmentManager.findFragmentByTag(LoadingDialogFragment().FRAGMENT_TAG) as LoadingDialogFragment
                loadingDialogFragment.dismiss()
            }
        }
    }


    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_upgradeFragment_to_profileFragment)
        }
    }

}