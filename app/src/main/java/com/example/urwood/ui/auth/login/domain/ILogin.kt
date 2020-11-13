package com.example.urwood.ui.auth.login.domain

import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult

interface ILogin {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Resource<AuthResult?>
}
