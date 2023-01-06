package com.sendiko.ternaqu.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentRegisterBinding
import com.sendiko.ternaqu.network.request.RegisterRequest
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.user.UserViewModel
import com.sendiko.ternaqu.ui.container.WelcomeActivity
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

private const val TAG = "RegisterFragment"

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

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
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowBack.setOnClickListener {
            requireContext().startActivity(Intent(context, WelcomeActivity::class.java))
        }

        binding.textToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.buttonRegister.setOnClickListener {
            val name = binding.inputUsername.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val passwordConfirmation = binding.inputConfirmPassword.text.toString()
            Log.e(TAG, "onViewCreated: $name, $email, $password, $passwordConfirmation")
            when {
                validate(name, email, password, passwordConfirmation) ->
                    userViewModel.postRegister(
                        RegisterRequest(
                            name, email, password, passwordConfirmation
                        )
                    ).observe(viewLifecycleOwner) {
                        when {
                            it -> findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
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

    private fun validate(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): Boolean {
        val valid: Boolean
        when {
            name.isEmpty() -> {
                valid = false
                binding.layoutName.error = "Name can't be empty"
            }
            email.isEmpty() -> {
                valid = false
                binding.layoutEmail.error = "Email can't be empty"
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                valid = false
                binding.layoutEmail.error = "Email must be a valid address"
            }
            password.isEmpty() -> {
                valid = false
                binding.layoutPassword.error = "Password can't be empty"
            }
            password.length < 8 -> {
                valid = false
                binding.layoutPassword.error = "Password must be 8 characters or more"
            }
            passwordConfirmation != password -> {
                valid = false
                binding.layoutConfirmPassword.error = "Password didn't match"
            }
            !binding.privacyPolicyCheck.isChecked -> {
                valid = false
                Toast.makeText(
                    context,
                    "You must agree with our privacy policy",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                valid = true
                binding.layoutName.error = null
                binding.layoutEmail.error = null
                binding.layoutPassword.error = null
                binding.layoutConfirmPassword.error = null
            }
        }
        return valid
    }

}