package com.example.urwood.ui.chat.discussion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.repository.model.Chat
import com.example.urwood.ui.main.home.adapter.ProdukAdapter
import com.google.android.material.textview.MaterialTextView
import de.hdodenhof.circleimageview.CircleImageView

class GroupChatAdapter(
    private val context: Context,
    private var dataset: List<Chat.Group>
) : RecyclerView.Adapter<GroupChatAdapter.ViewHolder>(){

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(groupChatModel: Chat.Group)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val groupData = dataset[position]
        holder.bind(groupData, onItemClickListener)
        holder.groupNameText.text = groupData.userData!!.name.toString()
        if (groupData.lastMessage != null) {
            holder.lastMessageText.text = groupData.lastMessage!!.message.toString()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun setGroupData(data: List<Chat.Group>) {
        this.dataset = data
    }

    fun getOnItemClickListener(): OnItemClickListener {
        return onItemClickListener
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var groupImage: CircleImageView = itemView.findViewById(R.id.profile_picture_group_chat)
        var groupNameText: MaterialTextView = itemView.findViewById(R.id.text_user_name_group_chat)
        var lastMessageText: MaterialTextView = itemView.findViewById(R.id.text_recent_chat_group_chat)

        var groupLayout: ConstraintLayout = itemView.findViewById(R.id.layout_group_item)

        fun bind(model: Chat.Group, listener: OnItemClickListener) {
            groupLayout.setOnClickListener {
                listener.onItemClick(model)
            }
        }

    }
}