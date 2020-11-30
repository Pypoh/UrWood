package com.example.urwood.repository.datasource.firestore.chat.api

import android.util.Log
import com.example.urwood.repository.model.Chat
import com.example.urwood.repository.model.User
import com.example.urwood.utils.CustomException
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatRepoImpl : IChatRepo {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val userReference: CollectionReference by lazy { db.collection("users") }
    private val chatReference: CollectionReference by lazy { db.collection("chat") }
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val userId: String by lazy { mAuth.currentUser!!.uid }

    private var groupList: ArrayList<String> = ArrayList()
    private var friendGroupList: ArrayList<String> = ArrayList()

    private var groupsData: ArrayList<Chat.Group> = ArrayList()

    private var messageData: ArrayList<Chat.Message> = arrayListOf()

    override suspend fun sendMessage(
        message: Chat.Message?,
        sendTo: String?,
        groupId: String?
    ): Resource<Int> {
        try {
            // Self Check
            if (sendTo == userId) {
                return Resource.Failure(CustomException("Same User"))
            }

            if (!groupId.isNullOrEmpty()) {
                message!!.sentBy = userId
                val messageReference =
                    chatReference.document(groupId).collection("messages").document()
                messageReference.set(message).await()
                messageReference.update("sentAt", FieldValue.serverTimestamp()).await()

                // Check friend id to add on db
                val groupIdArray = groupId.split("_")
                var friendId = ""
                for (ids in groupIdArray) {
                    if (ids != userId) {
                        friendId = ids
                    }
                }

                userReference.document(friendId).get().addOnSuccessListener {
                    friendGroupList = it.get("chatGroup") as ArrayList<String>
                }.await()

                var idFound = false
                for (ids in friendGroupList) {
                    val friendGroupIdArray = ids.split("_")
                    for (eachId in friendGroupIdArray) {
                        if (eachId == userId) {
                            idFound = true
                        }
                    }
                }

                Log.d("FriendDebug", "Status: $idFound")

                if (!idFound) {
                    Log.d("FriendDebug", "Friends Added")
                    friendGroupList.add(groupId)
                    userReference.document(friendId).update("chatGroup", friendGroupList).await()
                }

                return Resource.Success(201)
            }
            return Resource.Success(201)
        } catch (e: FirebaseFirestoreException) {
            return Resource.Failure(e)
        }
    }

    override suspend fun createGroup(targetPerson: String): Resource<String> {
        // Check My Existing Group
        userReference.document(userId).get().addOnSuccessListener {
            groupList = it.get("chatGroup") as ArrayList<String>
        }.await()


        // Update GroupArray in Firestore
//        var idFound = false
        for (ids in groupList) {
            val groupIdArray = ids.split("_")
            for (eachId in groupIdArray) {
                if (eachId == targetPerson) {
//                    idFound = true
                    return Resource.Success(ids)
                }
            }
        }

        val createdGroupId = "${userId}_${targetPerson}"

//        if (!idFound) {
        groupList.add(createdGroupId)
        userReference.document(userId).update("chatGroup", groupList).await()

//        // Add to friends group
//        userReference.document(targetPerson).get().addOnSuccessListener {
//            friendGroupList = it.get("chatGroup") as ArrayList<String>
//        }.await()
//
//        friendGroupList.add(createdGroupId)
//        userReference.document(targetPerson).update("chatGroup", friendGroupList).await()
//        }

        return Resource.Success(createdGroupId)

//        // Insert Chat Message
//        message!!.sentBy = userId
//        val messageReference =
//            chatReference.document(createdGroupId).collection("messages").document()
//        messageReference.set(message).await()
//        messageReference.update("sentAt", FieldValue.serverTimestamp()).await()

    }


    @ExperimentalCoroutinesApi
    override suspend fun getGroupChats(): Flow<Resource<List<Chat.Group>>> =
        callbackFlow {
            groupList.clear()
            userReference.document(userId).get().addOnSuccessListener {
                groupList = it.get("chatGroup") as ArrayList<String>
            }.await()

            val userGroupSubscription =
                userReference.document(userId).addSnapshotListener { snapshot, e ->
                    groupList.clear()
                    Log.d("ChatDebug", "Clearing...")
                    groupList = snapshot!!.get("chatGroup") as ArrayList<String>

                    for (groupId in groupList) {
                        val groupArray = groupId.split("_")
                        var otherUserId = ""
                        for (item in groupArray) {
                            if (userId != item) {
                                otherUserId = item
                            }
                        }

                        val otherUserData: User.UserData = User.UserData()
                        var lastMessageData: Chat.Message? = null

                        Log.d("ChatDebug", otherUserId)
                        userReference.document(otherUserId).get().addOnSuccessListener {
                            val userImage = it.get("image") as String?
                            val userName = it.get("name") as String?
                            Log.d("ChatDebug", "Nama: $userName")
                            otherUserData.image = userImage
                            otherUserData.name = userName
                            otherUserData.userId = otherUserId

                            chatReference.document(groupId).collection("messages")
                                .orderBy("sentAt", Query.Direction.DESCENDING).limit(1).get()
                                .addOnSuccessListener { query ->
                                    for (item in query) {
                                        lastMessageData = item.toObject(Chat.Message::class.java)
                                    }

                                    Log.d("ChatDebug", "Group Added")
                                    val groupData: Chat.Group =
                                        Chat.Group(groupId, otherUserData, lastMessageData)
                                    groupsData.add(groupData)

                                    offer(Resource.Success(groupsData))


                                }

                        }





                        Log.d("ChatDebug", "Next loop")

                    }

                    Log.d("ChatDebug", "Offering...")
//                    offer(Resource.Success(groupsData))
                }
            awaitClose { userGroupSubscription.remove() }

        }


//        return try {
//            //Check Existing Group
//            userReference.document(userId).get().addOnSuccessListener {
//                groupList = it.get("chatGroup") as ArrayList<String>
//            }.await()
//
//            for (groupId in groupList) {
//                val groupArray = groupId.split("_")
//                var otherUserId = ""
//                for (item in groupArray) {
//                    if (userId != item) {
//                        otherUserId = item
//                    }
//                }
//                var otherUserData: User.UserData = User.UserData()
//                var lastMessageData: Chat.Message? = null
//                userReference.document(otherUserId).get().addOnSuccessListener {
//                    val userImage = it.get("image") as String?
//                    val userName = it.get("name") as String?
//                    otherUserData.image = userImage
//                    otherUserData.name = userName
//                    otherUserData.userId = otherUserId
//                }.await()
//                chatReference.document(groupId).collection("messages")
//                    .orderBy("sentAt", Query.Direction.DESCENDING).limit(1).get()
//                    .addOnSuccessListener {
//                        for (item in it) {
//                            lastMessageData = item.toObject(Chat.Message::class.java)
//                        }
//                    }.await()
//
//                val groupData: Chat.Group = Chat.Group(groupId, otherUserData, lastMessageData)
//                groupsData.add(groupData)
//            }
//
//            Resource.Success(groupsData)
//        } catch (e: FirebaseFirestoreException) {
//            Resource.Failure(e)
//        }
//    }

    override suspend fun getMessages(groupId: String): Resource<ArrayList<Chat.Message>> {
        return try {
            chatReference.document(groupId).collection("messages")
                .orderBy("sentAt", Query.Direction.DESCENDING).get()
                .addOnSuccessListener {
                    for (item in it) {
                        val messageObject = item.toObject(Chat.Message::class.java)
                        if (messageObject.sentBy == userId) {
                            messageObject.messageType = 0
                        } else {
                            messageObject.messageType = 1
                        }
                        messageData.add(messageObject)
                    }
                }.await()

            Resource.Success(messageData)
        } catch (e: FirebaseFirestoreException) {
            Resource.Failure(e)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getMessagesFlow(groupId: String): Flow<Resource<ArrayList<Chat.Message>>> =
        callbackFlow {

            val messageRef = chatReference.document(groupId).collection("messages")

            val messageSubscription = messageRef.orderBy("sentAt", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    messageData.clear()
                    for (item in snapshot!!.documents) {
                        val messageObject = item.toObject(Chat.Message::class.java)
                        if (messageObject!!.sentBy == userId) {
                            messageObject.messageType = 0
                        } else {
                            messageObject.messageType = 1
                        }
                        messageData.add(messageObject)
                    }
                    offer(Resource.Success(messageData))
                }
            awaitClose { messageSubscription.remove() }
        }
}