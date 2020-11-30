package com.example.urwood.ui.chat.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.databinding.ActivityMessageBinding
import com.example.urwood.repository.datasource.auth.other.AuthRepoImpl
import com.example.urwood.repository.datasource.firestore.chat.api.ChatRepoImpl
import com.example.urwood.repository.model.Chat
import com.example.urwood.ui.auth.domain.AuthImpl
import com.example.urwood.ui.chat.domain.ChatVMFactory
import com.example.urwood.ui.chat.domain.ChatImpl
import com.example.urwood.ui.chat.messages.adapter.MessagesAdapter
import com.example.urwood.utils.viewobject.Resource
import com.google.android.material.textfield.TextInputEditText

class MessageActivity : AppCompatActivity() {

    private lateinit var messageBinding: ActivityMessageBinding

    private val messageViewModel: MessageViewModel by lazy {
        ViewModelProvider(
            this,
            ChatVMFactory(ChatImpl(ChatRepoImpl()), AuthImpl(AuthRepoImpl()))
        ).get(MessageViewModel::class.java)
    }

    private lateinit var recyclerMessage: RecyclerView
    private lateinit var adapterMessage: MessagesAdapter

    private lateinit var inputTextMessage: TextInputEditText
    private lateinit var buttonSendMessage: ImageView

    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val groupId = intent.getStringExtra("groupId")

        messageBinding = DataBindingUtil.setContentView(this, R.layout.activity_message)
        messageBinding.messageViewModel = messageViewModel

        setupViews(messageBinding.root)

        recyclerMessage.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        adapterMessage = MessagesAdapter(this, arrayListOf())
        recyclerMessage.adapter = adapterMessage

//        fetchMessageData(groupId!!)

        fetchMessagesFlowData(groupId!!)

        // Set Button Listener
        buttonSendMessage.setOnClickListener {
            Log.d("MessageDebug", "Button Clicked")
            messageViewModel.sendMessage(Chat.Message(inputTextMessage.text.toString(), null, null, -1), groupId)
            inputTextMessage.setText("")
            messageViewModel.messageSentResult.observe(this, { task ->
                when (task) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
//                        adapterMessage.setMessageData(task.data)
                        adapterMessage.notifyDataSetChanged()
                    }

                    is Resource.Failure -> {
                        Log.d("MessageDebug", "BUG: ${task.throwable.message}")
                    }
                }
            })
        }
    }

//    private fun fetchMessageData(groupId: String) {
//        messageViewModel.getMessages(groupId)
//        messageViewModel.messageResult.observe(this, { task ->
//            when (task) {
//                is Resource.Loading -> {
//                }
//
//                is Resource.Success -> {
//                    adapterMessage.setMessageData(task.data)
//                    adapterMessage.notifyDataSetChanged()
//                }
//
//                is Resource.Failure -> {
//                }
//            }
//        })
//    }

    private fun fetchMessagesFlowData(groupId: String) {
        messageViewModel.getMessagesFlow(groupId)
        messageViewModel.messageFlowResult.observe(this, { task ->
            when (task) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    adapterMessage.setMessageData(task.data)
                    adapterMessage.notifyDataSetChanged()
                }

                is Resource.Failure -> {
                }
            }
        })
    }

    private fun setupViews(view: View) {
        recyclerMessage = view.findViewById(R.id.recycler_message)
        backButton = view.findViewById(R.id.back_arrow_message)
        backButton.setOnClickListener {
            onBackPressed()
        }

        inputTextMessage = view.findViewById(R.id.iet_message)
        buttonSendMessage = view.findViewById(R.id.button_send_message)
    }
}