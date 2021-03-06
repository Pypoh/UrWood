package com.example.urwood.ui.auth.domain

import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface IAuth {
    suspend fun getAuthInstance(): Resource<FirebaseAuth>
    suspend fun getUserID(): Resource<FirebaseUser>
    suspend fun getUserData(): Resource<User.UserData?>

    suspend fun logout(): Resource<FirebaseAuth?>
}