package com.example.urwood.repository.datasource.auth.login

import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult

interface ILoginRepo {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Resource<AuthResult?>
}