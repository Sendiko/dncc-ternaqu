package com.sendiko.ternaqu.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentLoginBinding
import com.sendiko.ternaqu.network.request.LoginRequest
import com.sendiko.ternaqu.repository.AuthViewModel
import com.sendiko.ternaqu.repository.AuthViewModelFactory
import com.sendiko.ternaqu.repository.auth.AuthPreferences
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.user.UserViewModel
import com.sendiko.ternaqu.ui.auth.dataStore
import com.sendiko.ternaqu.ui.container.MainActivity
import com.sendiko.ternaqu.ui.container.WelcomeActivity
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

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
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowBack.setOnClickListener {
            requireContext().startActivity(Intent(context, WelcomeActivity::class.java))
        }

        binding.textToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        authViewModel.getTokenAccess().observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: $it")
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            when {
                validation(email, password) -> {
                    userViewModel.postLogin(
                        LoginRequest(
                            email, password
                        )
                    ).observe(viewLifecycleOwner) { token ->
                        when {
                            token != null -> {
                                userViewModel.user.observe(viewLifecycleOwner) {
                                    authViewModel.saveTokenAccess(token)
                                    authViewModel.setLoginState(true)
                                    authViewModel.saveUserID(it.id!!)
                                    authViewModel.saveUsername(it.name!!)
                                    startActivity(
                                        Intent(
                                            requireContext(),
                                            MainActivity::class.java
                                        )
                                    )
                                }
                            }
                        }
                    }
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

    private fun validation(
        email: String,
        password: String
    ): Boolean {
        val valid: Boolean
        Log.d(TAG, "validation: $email, $password")
        when {
            email.isEmpty() -> {
                valid = false
                binding.layoutEmail.error = "Email can't be empty"
                binding.layoutPassword.error = null
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                valid = false
                binding.layoutEmail.error = "Email must be a valid address"
                binding.layoutPassword.error = null
            }
            password.isEmpty() -> {
                valid = false
                binding.layoutPassword.error = "Password can't be empty"
                binding.layoutEmail.error = null
            }
            password.length < 8 -> {
                valid = false
                binding.layoutPassword.error = "Password must be 8 characters or more"
                binding.layoutEmail.error = null
            }
            else -> valid = true
        }
        return valid
    }

}