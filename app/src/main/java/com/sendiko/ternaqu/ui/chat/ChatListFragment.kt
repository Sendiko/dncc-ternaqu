package com.sendiko.ternaqu.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentChatListBinding
import com.sendiko.ternaqu.repository.chat.Chat
import com.sendiko.ternaqu.repository.chat.ChatRepository
import com.sendiko.ternaqu.repository.helper.SharedViewModel

class ChatListFragment : Fragment() {

    private lateinit var binding: FragmentChatListBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_chatListFragment_to_dashboardFragment)
        }

        binding.rvNutritionists.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ChatListAdapter(ChatRepository().getChatList(), requireContext(), object : ChatListAdapter.OnItemClick{
                override fun onCardRecipeClick(chat: Chat) {
                    sharedViewModel.saveChatInfo(chat)
                    findNavController().navigate(R.id.action_chatListFragment_to_chatFragment)
                }

            })
        }

    }

}