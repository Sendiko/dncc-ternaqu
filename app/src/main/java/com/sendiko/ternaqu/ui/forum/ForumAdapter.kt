package com.sendiko.ternaqu.ui.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.databinding.CardTopicBinding
import com.sendiko.ternaqu.network.response.TopicsItem

class ForumAdapter(
    private val topics: ArrayList<TopicsItem>,
    private val context: Context,
    private val onItemClick: OnItemClick
): RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {

    class ForumViewHolder(
        val binding: CardTopicBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val binding = CardTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val currentItem = topics[position]
        Glide.with(context)
            .load(currentItem.profileUrl)
            .circleCrop()
            .into(holder.binding.imageView9)
        holder.binding.textNameUser.text = currentItem.name
        holder.binding.textTitle.text = currentItem.title
        holder.binding.textView24.text = currentItem.question
        holder.binding.root.setOnClickListener {
            onItemClick.OnReplyCardClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    interface OnItemClick{
        fun OnReplyCardClick(topic: TopicsItem)
    }

}