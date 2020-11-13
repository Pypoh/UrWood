package com.example.urwood.repository.datasource.auth.login

import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await

class LoginRepoImpl : ILoginRepo {
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<AuthResult?> {
        return try {
            val data = mAuth
                .signInWithEmailAndPassword(email, password)
                .await()
            Resource.Success(data)
        } catch (e: FirebaseAuthException) {
            Resource.Failure(e)
        }
    }
}