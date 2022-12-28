package com.sendiko.ternaqu.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentRegisterBinding
import com.sendiko.ternaqu.ui.container.WelcomeActivity

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

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
            when(validate(name, email, password, passwordConfirmation)){
                true -> Toast.makeText(context, "valid", Toast.LENGTH_SHORT).show()
                else -> {
                    Toast.makeText(context, "not valid", Toast.LENGTH_SHORT).show()}
            }
        }

    }

    private fun validate(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): Boolean {
        var valid = false
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