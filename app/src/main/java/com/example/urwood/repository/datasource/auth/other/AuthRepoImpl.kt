package com.example.urwood.repository.datasource.auth.other

import android.util.Log
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepoImpl : IAuthRepo {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var mUser: FirebaseUser

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private var userData: User.UserData? = null

    override suspend fun getAuthInstance(): Resource<FirebaseAuth> {
        return try {
            Resource.Success(mAuth)
        } catch (e: FirebaseAuthException) {
            Resource.Failure(e)
        }
    }

    override suspend fun getUserID(): Resource<FirebaseUser> {
        return try {
            mUser = mAuth.currentUser!!

            Resource.Success(mUser)
        } catch (e: FirebaseAuthInvalidUserException) {
            Resource.Failure(e)
        }
    }

    override suspend fun getUserData(): Resource<User.UserData?> {
        return try {
            getUserID()
            Log.d("UserDebug", mUser.uid)
            db.collection("users").document(mUser.uid).get().addOnSuccessListener {
                userData = it.toObject(User.UserData::class.java)
            }.await()

            Resource.Success(userData)
        } catch (e: FirebaseAuthInvalidUserException) {
            Resource.Failure(e)
        }
    }

    override suspend fun logout(): Resource<FirebaseAuth?> {
        return try {
            mAuth.signOut()
            Resource.Success(mAuth)
        } catch (e: FirebaseAuthException) {
            Resource.Failure(e)
        }
    }
}