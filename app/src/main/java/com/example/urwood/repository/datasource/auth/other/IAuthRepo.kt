package com.example.urwood.repository.datasource.auth.other

import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface IAuthRepo {
    suspend fun getAuthInstance(): Resource<FirebaseAuth>
    suspend fun getUserID(): Resource<FirebaseUser>

    suspend fun logout(): Resource<FirebaseAuth?>
}