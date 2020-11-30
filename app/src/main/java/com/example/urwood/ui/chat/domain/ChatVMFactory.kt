package com.example.urwood.ui.chat.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.auth.domain.IAuth
import com.google.firebase.auth.FirebaseAuth

class ChatVMFactory(private val useCaseChat: IChat, private val useCaseAuth: IAuth) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IChat::class.java, IAuth::class.java).newInstance(useCaseChat, useCaseAuth)
    }
}