package com.example.urwood.repository.datasource.auth.signup

import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult

interface ISignupRepo {
    suspend fun registerWithEmailAndPassword(email: String, password: String): Resource<AuthResult?>
    suspend fun insertUserData(userData: User.UserData)
}