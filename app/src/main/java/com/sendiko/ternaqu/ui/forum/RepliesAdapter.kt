package com.sendiko.ternaqu.ui.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.databinding.CardRepliesBinding
import com.sendiko.ternaqu.network.response.RepliesItem

class RepliesAdapter(
    private val replies: ArrayList<RepliesItem>,
    private val context: Context
): RecyclerView.Adapter<RepliesAdapter.RepliesViewHolder>() {
    class RepliesViewHolder(
        val binding: CardRepliesBinding
    ): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepliesViewHolder {
        val binding = CardRepliesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepliesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepliesViewHolder, position: Int) {
        val currentItem = replies[position]
        holder.binding.textView15.text = currentItem.title
        "Oleh ${currentItem.name}, ${currentItem.createdAt}".also { holder.binding.textView17.text = it }
        holder.binding.textView18.text = currentItem.question
        Glide.with(context)
            .load(currentItem.profileUrl)
            .circleCrop()
            .into(holder.binding.imageView8)
    }

    override fun getItemCount(): Int {
        return replies.size
    }
}