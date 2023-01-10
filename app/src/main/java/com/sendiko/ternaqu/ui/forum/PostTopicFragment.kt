package com.sendiko.ternaqu.ui.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentPostTopicBinding
import com.sendiko.ternaqu.network.request.TopicRequest
import com.sendiko.ternaqu.repository.AuthViewModel
import com.sendiko.ternaqu.repository.AuthViewModelFactory
import com.sendiko.ternaqu.repository.auth.AuthPreferences
import com.sendiko.ternaqu.repository.forum.ForumViewModel
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.ui.auth.dataStore
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

class PostTopicFragment : Fragment() {

    private lateinit var binding: FragmentPostTopicBinding

    private fun obtainViewModel(activity: FragmentActivity): ForumViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[ForumViewModel::class.java]
    }

    private val forumViewModel by lazy {
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
        binding = FragmentPostTopicBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPost.setOnClickListener {
            val title = binding.inputTitle.text.toString()
            val question = binding.inputQuestion.text.toString()
            authViewModel.getUserID().observe(viewLifecycleOwner) { userId ->
                authViewModel.getTokenAccess().observe(viewLifecycleOwner) { token ->
                    forumViewModel.postTopic(
                        token, TopicRequest(
                            userId,
                            title,
                            question,
                            0
                        )
                    ).observe(viewLifecycleOwner) {
                        when (it) {
                            true -> findNavController().navigate(R.id.action_postTopicFragment_to_forumFragment)
                            else -> {}
                        }
                    }
                }
            }
        }

        forumViewModel.isFailed.observe(viewLifecycleOwner) {
            when {
                it.isFailed -> showSnackbar(it.failedMessage)
            }
        }

        forumViewModel.isLoading.observe(viewLifecycleOwner) {
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

}