package com.example.urwood.ui.chat.domain

import com.example.urwood.repository.datasource.firestore.chat.api.IChatRepo
import com.example.urwood.repository.model.Chat
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.flow.Flow

class ChatImpl(private val chatRepository: IChatRepo) : IChat {
    override suspend fun sendMessage(
        message: Chat.Message?,
        sendTo: String?,
        groupId: String?
    ): Resource<Int> =
        chatRepository.sendMessage(message, sendTo, groupId)

    override suspend fun createGroup(targetPerson: String): Resource<String> =
        chatRepository.createGroup(targetPerson)

    override suspend fun getGroupChats(): Flow<Resource<List<Chat.Group>>> =
        chatRepository.getGroupChats()

    override suspend fun getMessages(groupId: String): Resource<ArrayList<Chat.Message>> =
        chatRepository.getMessages(groupId)

    override suspend fun getMessagesFlow(groupId: String): Flow<Resource<ArrayList<Chat.Message>>> =
        chatRepository.getMessagesFlow(groupId)
}