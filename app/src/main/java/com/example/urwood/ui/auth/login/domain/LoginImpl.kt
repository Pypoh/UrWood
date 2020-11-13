package com.example.urwood.ui.auth.login.domain

import com.example.urwood.repository.datasource.auth.login.ILoginRepo
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult

class LoginImpl(private val loginRepository: ILoginRepo) : ILogin {
    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<AuthResult?> = loginRepository.loginWithEmailAndPassword(email, password)
}
