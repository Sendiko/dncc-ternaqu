package com.sendiko.ternaqu.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentProfileBinding
import com.sendiko.ternaqu.repository.AuthViewModel
import com.sendiko.ternaqu.repository.AuthViewModelFactory
import com.sendiko.ternaqu.repository.auth.AuthPreferences
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.user.UserViewModel
import com.sendiko.ternaqu.ui.auth.dataStore
import com.sendiko.ternaqu.ui.container.WelcomeActivity
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private fun obtainViewModel(activity: FragmentActivity): UserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private val userViewModel by lazy {
        obtainViewModel(requireNotNull(this.activity))
    }

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
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()

        binding.cardInsight.setOnClickListener {
            val repoUrl = "https://github.com/Sendiko/dncc-ternaqu/issues"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(repoUrl)
            startActivity(intent)
        }

        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_dashboardFragment)
        }

        binding.cardLogout.setOnClickListener {
            authViewModel.getTokenAccess().observe(viewLifecycleOwner) {
                userViewModel.postLogout(it).observe(viewLifecycleOwner){ isSuccess ->
                    when (isSuccess) {
                        true -> {
                            authViewModel.logoutApp().also {
                                requireContext().startActivity(
                                    Intent(
                                        requireContext(),
                                        WelcomeActivity::class.java
                                    )
                                )
                            }
                        }
                        else -> {}
                    }
                }
            }
        }

        authViewModel.getTokenAccess().observe(viewLifecycleOwner) {
            userViewModel.getUser(it).observe(viewLifecycleOwner) { user ->
                Glide.with(requireContext())
                    .load(user.profileUrl)
                    .circleCrop()
                    .into(binding.imageView11)

                binding.textNameUser.text = user.name
                binding.textEmail.text = user.email
                when (user.premium) {
                    "0" -> {
                        binding.button4.text = getString(R.string.go_premium)
                        binding.button4.setOnClickListener {
                            findNavController().navigate(R.id.action_profileFragment_to_upgradeFragment)
                        }
                    }
                    "1" -> binding.button4.text = getString(R.string.is_premium)
                }
            }
        }

        userViewModel.isFailed.observe(viewLifecycleOwner) {
            when {
                it.isFailed -> showSnackbar(it.failedMessage)
            }
        }

        userViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
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
            findNavController().navigate(R.id.action_profileFragment_to_dashboardFragment)
        }
    }

}