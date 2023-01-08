package com.sendiko.ternaqu.ui.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentDetailsForumBinding
import com.sendiko.ternaqu.repository.forum.ForumViewModel
import com.sendiko.ternaqu.repository.helper.SharedViewModel
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

private const val TAG = "DetailsForumFragment"
class DetailsForumFragment : Fragment() {

    private lateinit var binding: FragmentDetailsForumBinding

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
        binding = FragmentDetailsForumBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailsForumFragment_to_forumFragment)
        }

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_detailsForumFragment_to_postReplyFragment)
        }

        sharedViewModel.topic.observe(viewLifecycleOwner) {

            Glide.with(requireContext())
                .load(it.profileUrl)
                .circleCrop()
                .into(binding.imageView7)

            binding.textView13.text = it.title
            binding.textView14.text = buildString {
                append("Oleh ")
                append(it.name)
                append(", ")
                append(it.createdAt)
            }
            binding.textView16.text = it.question

            forumViewModel.getTopic(it.id.toString()).observe(viewLifecycleOwner){
                binding.rvReplies.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = RepliesAdapter(it, requireContext())
                }

                binding.textCount.text = buildString {
                    append(it.size)
                    append(" jawaban")
                }

            }

            Log.i(TAG, "onViewCreated: $it")
        }

        forumViewModel.isFailed.observe(viewLifecycleOwner){
            when {
                it.isFailed -> showSnackbar(it.failedMessage)
            }
        }

        forumViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
            when(it){
                true -> binding.textCount.visibility = View.GONE
                else -> binding.textCount.visibility = View.VISIBLE
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

}