package com.sendiko.ternaqu.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentChatBinding
import com.sendiko.ternaqu.repository.chat.ChatRepository

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView15.setOnClickListener {
            findNavController().navigate(R.id.action_chatFragment_to_chatListFragment)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MessageAdapter(ChatRepository().getMessageList(), requireContext())
        }

    }
}