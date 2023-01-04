package com.sendiko.ternaqu.ui.forum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentForumBinding
import com.sendiko.ternaqu.repository.forum.ForumTopicRepository

class ForumFragment : Fragment() {

    private lateinit var binding : FragmentForumBinding

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

        binding.rvTopics.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ForumAdapter(ForumTopicRepository().getForumTopic(), requireContext())
            setHasFixedSize(true)
        }

    }

    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_forumFragment_to_dashboardFragment)
        }
    }

}