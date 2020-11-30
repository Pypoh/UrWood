package com.example.urwood.ui.chat.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.Chat
import com.example.urwood.ui.auth.domain.IAuth
import com.example.urwood.ui.chat.domain.IChat
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class MessageViewModel(private val useCaseChat: IChat, private val useCaseAuth: IAuth) : ViewModel() {

    lateinit var messageResult: LiveData<Resource<ArrayList<Chat.Message>>>

    lateinit var messageFlowResult: LiveData<Resource<ArrayList<Chat.Message>>>

    lateinit var messageSentResult: LiveData<Resource<Int>>


//    fun getMessages(groupId: String) {
//        messageResult = liveData(Dispatchers.IO) {
//            emit(Resource.Loading())
//            try {
//                val data = useCaseChat.getMessages(groupId)
//                emit(data)
//            } catch (e: Exception) {
//                emit(Resource.Failure(e))
//            }
//        }
//    }

    fun sendMessage(chatMessage: Chat.Message, groupId: String) {
        messageSentResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseChat.sendMessage(chatMessage, null, groupId)
                emit(data)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun getMessagesFlow(groupId: String) {
        messageFlowResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                useCaseChat.getMessagesFlow(groupId).collect {
                    emit(it)
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}