package com.sendiko.ternaqu.ui.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.CardTopicBinding
import com.sendiko.ternaqu.repository.model.ForumTopic

class ForumAdapter(
    private val topics: ArrayList<ForumTopic>,
    private val context: Context
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
            .load(currentItem.imageUrl)
            .circleCrop()
            .fitCenter()
            .into(holder.binding.imageView9)
        holder.binding.textNameUser.text = currentItem.name
        holder.binding.textTitle.text = currentItem.title
        holder.binding.textView24.text = currentItem.question
    }

    override fun getItemCount(): Int {
        return topics.size
    }

}