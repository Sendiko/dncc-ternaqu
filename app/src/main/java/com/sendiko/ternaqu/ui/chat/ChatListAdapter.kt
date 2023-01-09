package com.sendiko.ternaqu.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.databinding.CardNutritionistBinding
import com.sendiko.ternaqu.repository.chat.Chat

class ChatListAdapter(
    private val chat: ArrayList<Chat>,
    private val context: Context
): RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    class ChatListViewHolder(val binding: CardNutritionistBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val binding = CardNutritionistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val currentItem = chat[position]
        holder.binding.textView34.text = currentItem.name
        holder.binding.textView35.text = currentItem.message
        Glide.with(context)
            .load(currentItem.profileUrl)
            .circleCrop()
            .into(holder.binding.imageView14)
    }

    override fun getItemCount(): Int {
        return chat.size
    }

}