package com.example.urwood.repository.datasource.auth.signup

import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class SignupRepoImpl : ISignupRepo {
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val mRef: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<AuthResult?> {
        return try {
            val data = mAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            insertUserData(User.UserData(email))

            Resource.Success(data)
        } catch (e: FirebaseAuthException) {
            Resource.Failure(e)
        }
    }

    override suspend fun insertUserData(userData: User.UserData) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef: DocumentReference = mRef.collection("users").document(userId.toString())
        return try {
            val data = userRef.set(userData).await()
        } catch (e: FirebaseFirestoreException) {

        }
    }

}