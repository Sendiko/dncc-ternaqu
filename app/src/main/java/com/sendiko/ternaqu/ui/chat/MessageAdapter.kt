package com.sendiko.ternaqu.ui.chat

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.MessageBubbleBinding
import com.sendiko.ternaqu.repository.chat.Message

class MessageAdapter(
    private val messages: ArrayList<Message>,
    private val context: Context
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(val binding: MessageBubbleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding =
            MessageBubbleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentItem = messages[position]
        holder.binding.textView41.text = currentItem.message
        when (currentItem.isRead) {
            true -> holder.binding.imageView17.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.textHintColor
                ), PorterDuff.Mode.SRC_IN
            )
            else -> holder.binding.imageView17.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.lightGreen
                ), PorterDuff.Mode.SRC_IN
            )
        }
        when(currentItem.from){
            "HOME" -> {
                holder.binding.bubbleMessage.setCardBackgroundColor(context.getColor(R.color.whiteBgAlt))
                holder.binding.textView41.setTextColor(context.getColor(R.color.textColor))
            }
            "AWAY" -> {
                holder.binding.bubbleMessage.setCardBackgroundColor(context.getColor(R.color.secondaryBlue))
                holder.binding.textView41.setTextColor(context.getColor(R.color.whiteBg))
                holder.binding.imageView17.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

}