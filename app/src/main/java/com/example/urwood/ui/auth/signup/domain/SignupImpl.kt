package com.example.urwood.ui.auth.signup.domain

import com.example.urwood.repository.datasource.auth.signup.ISignupRepo
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult

class SignupImpl(private val signupRepository: ISignupRepo) : ISignup {
    override suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<AuthResult?> = signupRepository.registerWithEmailAndPassword(email, password)
}