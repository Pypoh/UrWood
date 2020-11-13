package com.example.urwood.ui.auth.signup.domain

import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult

interface ISignup {
    suspend fun registerWithEmailAndPassword(email: String, password: String): Resource<AuthResult?>
}