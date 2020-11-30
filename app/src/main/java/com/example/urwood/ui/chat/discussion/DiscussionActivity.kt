package com.example.urwood.ui.chat.discussion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.databinding.ActivityDiscussionBinding
import com.example.urwood.repository.datasource.auth.other.AuthRepoImpl
import com.example.urwood.repository.datasource.firestore.chat.api.ChatRepoImpl
import com.example.urwood.repository.model.Chat
import com.example.urwood.ui.auth.domain.AuthImpl
import com.example.urwood.ui.chat.discussion.adapter.GroupChatAdapter
import com.example.urwood.ui.chat.domain.ChatImpl
import com.example.urwood.ui.chat.domain.ChatVMFactory
import com.example.urwood.ui.chat.messages.MessageActivity
import com.example.urwood.ui.main.profile.store.StoreActivity
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.InternalCoroutinesApi

class DiscussionActivity : AppCompatActivity() {

    private lateinit var discussionBinding: ActivityDiscussionBinding

    private val discussionViewModel: DiscussionViewModel by lazy {
        ViewModelProvider(
            this,
            ChatVMFactory(ChatImpl(ChatRepoImpl()), AuthImpl(AuthRepoImpl()))
        ).get(DiscussionViewModel::class.java)
    }

    private lateinit var adapterGroupChat: GroupChatAdapter
    private lateinit var recyclerGroupChat: RecyclerView

    private lateinit var backButton: ImageView

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discussion)

        discussionBinding = DataBindingUtil.setContentView(this, R.layout.activity_discussion)
        discussionBinding.discussionViewModel = discussionViewModel

        setupViews(discussionBinding.root)

        // RecyclerView
        recyclerGroupChat.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapterGroupChat = GroupChatAdapter(this, ArrayList())
        recyclerGroupChat.adapter = adapterGroupChat
        adapterGroupChat.setOnItemClickListener(object : GroupChatAdapter.OnItemClickListener {
            override fun onItemClick(groupChatModel: Chat.Group) {
                intentToChat(groupChatModel)
            }
        })

        fetchGroupData()

    }

    private fun intentToChat(groupChatModel: Chat.Group) {
        val intent = Intent(this, MessageActivity::class.java)
        intent.putExtra("groupId", groupChatModel.groupId)
        startActivity(intent)
    }

    @InternalCoroutinesApi
    private fun fetchGroupData() {
        discussionViewModel.getGroupsData()
        discussionViewModel.chatGroupResult.observe(this, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    adapterGroupChat.setGroupData(task.data)
                    adapterGroupChat.notifyDataSetChanged()
                }

                is Resource.Failure -> {

                }
            }
        })
    }

    private fun setupViews(view: View) {
        recyclerGroupChat = view.findViewById(R.id.recycler_chat_group)
        backButton = view.findViewById(R.id.back_arrow_discussion)
        backButton.setOnClickListener {
            onBackPressed()
        }

    }
}