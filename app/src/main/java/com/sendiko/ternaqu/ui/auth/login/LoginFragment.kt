package com.sendiko.ternaqu.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentLoginBinding
import com.sendiko.ternaqu.repository.AuthPreferences
import com.sendiko.ternaqu.repository.AuthViewModel
import com.sendiko.ternaqu.repository.AuthViewModelFactory
import com.sendiko.ternaqu.ui.auth.dataStore
import com.sendiko.ternaqu.ui.container.WelcomeActivity

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

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

        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            when (validation(email, password)) {
                true -> authViewModel.setLoginState(true)
                else -> {}
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