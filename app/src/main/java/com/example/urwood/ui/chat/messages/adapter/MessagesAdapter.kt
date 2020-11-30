package com.example.urwood.ui.chat.messages.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.repository.model.Chat

class MessagesAdapter(var context: Context, var data: ArrayList<Chat.Message>) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<*> {
        return when (viewType) {
            0 -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.item_my_message, parent, false)
                MessageViewHolder.MyMessageViewHolder(view)
            }
            1 -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_friend_message, parent, false)
                MessageViewHolder.FriendMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder<*>, position: Int) {
        val messageData = data[position]
        when (holder) {
            is MessageViewHolder.MyMessageViewHolder -> holder.bind(messageData)
            is MessageViewHolder.FriendMessageViewHolder -> holder.bind(messageData)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].messageType
    }

    fun setMessageData(data: ArrayList<Chat.Message>) {
        this.data = data
    }

    abstract class MessageViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)

        class MyMessageViewHolder(val view: View) : MessageViewHolder<Chat.Message>(view) {
            private val messageContent = view.findViewById<TextView>(R.id.message)
            override fun bind(item: Chat.Message) {
                messageContent.text = item.message
            }
        }

        class FriendMessageViewHolder(val view: View) : MessageViewHolder<Chat.Message>(view) {
            private val messageContent = view.findViewById<TextView>(R.id.message)
            override fun bind(item: Chat.Message) {
                messageContent.text = item.message
            }
        }
    }
}