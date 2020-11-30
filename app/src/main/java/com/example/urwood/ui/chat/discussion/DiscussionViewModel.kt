package com.example.urwood.ui.chat.discussion

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.Chat
import com.example.urwood.ui.auth.domain.IAuth
import com.example.urwood.ui.chat.domain.IChat
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class DiscussionViewModel(private val useCaseChat: IChat, private val useCaseAuth: IAuth) : ViewModel() {

    lateinit var chatGroupResult: LiveData<Resource<List<Chat.Group>>>

//    fun sendMessage(message: Chat.Message, sendTo: String) {
//        chatGroupResult = liveData(Dispatchers.IO) {
//            emit(Resource.Loading())
//            try {
//                val data = useca
//            }
//        }
//    }

    @InternalCoroutinesApi
    fun getGroupsData() {
        chatGroupResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                useCaseChat.getGroupChats().collect {
                    emit(it)
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}