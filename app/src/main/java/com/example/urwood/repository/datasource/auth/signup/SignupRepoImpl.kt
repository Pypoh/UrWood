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

            Resource.Success(data)
        } catch (e: FirebaseAuthException) {
            Resource.Failure(e)
        }
    }

//    override fun insertUserData(name: String, email: String) {
//        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        val userRef: DocumentReference = mRef.collection("users").document(userId.toString())
//        val userModel = User.UserRegister(name, email)
//        return try {
//            val  data = userRef.set(userModel).addOnSuccessListener{
//
//            }
//        } catch (e: FirebaseFirestoreException) {
//
//        }
//    }
}