package com.sendiko.ternaqu.ui.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentForumBinding
import com.sendiko.ternaqu.network.response.TopicsItem
import com.sendiko.ternaqu.repository.forum.ForumViewModel
import com.sendiko.ternaqu.repository.helper.SharedViewModel
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.ui.forum.ForumAdapter.OnItemClick
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

class ForumFragment : Fragment() {

    private lateinit var binding: FragmentForumBinding

    private val sharedViewModel : SharedViewModel by activityViewModels()

    private fun obtainViewModel(activity: FragmentActivity): ForumViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[ForumViewModel::class.java]
    }

    private val forumViewModel by lazy {
        obtainViewModel(requireNotNull(this.activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForumBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_forumFragment_to_dashboardFragment)
        }

        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_forumFragment_to_postTopicFragment)
        }

        forumViewModel.getTopics().observe(viewLifecycleOwner) {
            binding.rvTopics.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ForumAdapter(it, requireContext(), object : OnItemClick{
                    override fun OnReplyCardClick(topic: TopicsItem) {
                        sharedViewModel.saveTopic(topic)
                        findNavController().navigate(R.id.action_forumFragment_to_detailsForumFragment)
                    }

                })
                setHasFixedSize(true)
            }
        }

        forumViewModel.isFailed.observe(viewLifecycleOwner){
            when {
                it.isFailed -> showSnackbar(it.failedMessage)
            }
        }

        forumViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        forumViewModel.isEmpty.observe(viewLifecycleOwner){
            when(it){
                true -> binding.dataEmpty.visibility = View.VISIBLE
                else -> binding.dataEmpty.visibility = View.GONE
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
            findNavController().navigate(R.id.action_forumFragment_to_dashboardFragment)
        }
    }

}