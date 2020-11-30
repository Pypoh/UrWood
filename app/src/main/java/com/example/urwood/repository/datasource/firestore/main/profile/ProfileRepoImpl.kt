package com.example.urwood.repository.datasource.firestore.main.profile

import android.util.Log
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await

class ProfileRepoImpl : IProfileRepo {
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val userReference: CollectionReference by lazy { db.collection("users") }
    private val mUser: FirebaseUser by lazy { FirebaseAuth.getInstance().currentUser!! }

    override suspend fun updateUserData(userData: User.UserData) : Resource<Int> {
        return try {
            val userUpdateRef = userReference.document(mUser.uid)
            userUpdateRef.update("image", userData.image)
            userUpdateRef.update("email", userData.email)
            userUpdateRef.update("name", userData.name)
            userUpdateRef.update("phoneNumber", userData.phoneNumber).await()
            Resource.Success(201)
        } catch (e: FirebaseFirestoreException) {
            Resource.Failure(e)
        }
    }
    override suspend fun updateStoreData(storeData: User.Store): Resource<Int> {
        return try {
            val storeReference = userReference.document(mUser.uid).collection("store").document(storeData.storeId!!)
            storeReference.update("storeName", storeData.storeName)
            storeReference.update("storeDescription", storeData.storeDescription)
            Resource.Success(201)
        } catch (e: FirebaseFirestoreException) {
            Resource.Failure(e)
        }
    }
}