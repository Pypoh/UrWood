package com.example.urwood.repository.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

object Chat {

    data class Group(
        var groupId: String? = null,
        var userData: User.UserData? = null,
        var lastMessage: Message? = null
    )

    data class Message(
        var message: String? = "",
        @ServerTimestamp var sentAt: Date? = null,
        var sentBy: String? = "",
        @Exclude
        var messageType: Int = -1 // 0 = my, 1 = friend
    )

}