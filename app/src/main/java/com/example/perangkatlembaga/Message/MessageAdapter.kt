package com.example.perangkatlembaga.Message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perangkatlembaga.databinding.ItemMessageBinding

class MessageAdapter(private val messageList: List<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        holder.binding.apply {
            tvSenderName.text = message.sender
            tvMessageContent.text = message.content
            tvTime.text = message.time
            ivSenderIcon.setImageResource(message.iconRes)
        }
    }

    override fun getItemCount(): Int = messageList.size
}