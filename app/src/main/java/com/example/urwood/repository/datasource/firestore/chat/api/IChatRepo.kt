package com.example.urwood.repository.datasource.firestore.chat.api

import com.example.urwood.repository.model.Chat
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.flow.Flow

interface IChatRepo {

    suspend fun sendMessage(message: Chat.Message?, sendTo: String?, groupId: String?): Resource<Int>

    suspend fun createGroup(targetPerson: String): Resource<String>

    suspend fun getGroupChats(): Flow<Resource<List<Chat.Group>>>

    suspend fun getMessages(groupId: String): Resource<ArrayList<Chat.Message>>

    suspend fun getMessagesFlow(groupId: String) : Flow<Resource<ArrayList<Chat.Message>>>

}