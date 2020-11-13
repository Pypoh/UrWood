package com.example.urwood.ui.auth.domain

import com.example.urwood.repository.datasource.auth.other.IAuthRepo
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthImpl(private val authRepository: IAuthRepo) : IAuth{
    override suspend fun getAuthInstance(): Resource<FirebaseAuth> = authRepository.getAuthInstance()
    override suspend fun getUserID(): Resource<FirebaseUser> = authRepository.getUserID()
}